package com.godzillajim.betterprogramming.services;

import com.godzillajim.betterprogramming.domain.entities.blog.Blog;
import com.godzillajim.betterprogramming.domain.entities.blog.Category;
import com.godzillajim.betterprogramming.domain.entities.blog.Comment;
import com.godzillajim.betterprogramming.domain.entities.blog.Tag;
import com.godzillajim.betterprogramming.domain.entities.users.User;
import com.godzillajim.betterprogramming.domain.mappers.BlogBody;
import com.godzillajim.betterprogramming.domain.mappers.BlogMappers;
import com.godzillajim.betterprogramming.domain.mappers.CommentBody;
import com.godzillajim.betterprogramming.domain.mappers.TagBody;
import com.godzillajim.betterprogramming.domain.repositories.IUserRepository;
import com.godzillajim.betterprogramming.errors.BadRequestException;
import com.godzillajim.betterprogramming.errors.ResourceNotFoundException;
import com.godzillajim.betterprogramming.repositories.BlogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BlogService {
    private final BlogRepository blogRepository;
    private final CommentService commentService;
    private final CategoryService categoryService;
    private final TagService tagService;
    private final IUserRepository userRepository;

    public List<Blog> getArchivedBlogs(){
        return blogRepository.findBlogByArchivedTrue();
    }
    public List<BlogBody> getAllBlogs(){
        List<BlogBody> bodies = new ArrayList<>();
        List<Blog> blogs = blogRepository.findAll();
        blogs.forEach((blog)-> {
            List<Comment> comments = commentService.getCommentsByBlog(blog);
            bodies.add(BlogMappers.mapBlogToBlogBody(blog, comments));
        });
        return bodies;
    }
    public BlogBody getBlogDetails(Long id) throws ResourceNotFoundException {
        Blog blog = blogRepository
                .findBlogById(id)
                .orElseThrow(()-> new ResourceNotFoundException(String.format("The blog with id %s does not exist.", id.toString())));
        BlogBody blogBody;
        List<Comment> comments = commentService.getCommentsByBlog(blog);
        List<Blog> relatedBlogs = blogRepository
                .findTop5ByTitleContainsOrContentContainsOrCategory(blog.getTitle(),
                blog.getTitle(), blog.getCategory());
        blogBody = BlogMappers.mapBlogToBlogBody(blog,comments);
        List<Blog> filteredBlogs = relatedBlogs.stream().filter(relatedBlog -> blog.getId() != relatedBlog.getId()).collect(
                Collectors.toList());
        blogBody.setRelatedBlogs(filteredBlogs);
        return blogBody;
    }
    @Transactional
    public Blog updateBlog(Long id, BlogBody blogBody){
        Blog blog = blogRepository
                .findBlogById(id)
                .orElseThrow(
                        ()-> new ResourceNotFoundException(String.format("The blog with id %s does not exist.", id.toString())));
        String title = blogBody.getTitle();
        String content = blogBody.getContent();
        String summary = blogBody.getSummary();
        String prompt = blogBody.getPrompt();
        blog.setTitle(title);
        blog.setContent(content);
        blog.setSummary(summary);
        blog.setPrompt(prompt);
        blog.setImg(blogBody.getImg());
        blog.setAuthor(blogBody.getAuthor());
        blog.setUpdatedBy(blogBody.getUpdatedBy());
        List<Tag> tags = new ArrayList<>();
        blogBody.getTags().forEach(tagBody -> {
            TagBody newTag = tagService.createTag(tagBody);
            Tag tag = tagService.getTagById(newTag.getId());
            tags.add(tag);
        });
        blog.setTags(tags);
        return blogRepository.save(blog);
    }
    public Blog createBlog(BlogBody body){
        Blog testBlog = blogRepository.findBlogByTitle(body.getTitle()).orElse(null);
        if(testBlog != null){
            throw new BadRequestException(String.format("This title already exists: {%s}", body.getTitle()));
        }
        Category category = categoryService.getCategoryByName(body.getCategory());
        List<Tag> tags = new ArrayList<>();
        body.getTags().forEach(tagBody -> {
            TagBody t = tagService.createTag(tagBody);
            Tag tag=tagService.getTagById(t.getId());
            tags.add(tag);
        });
        Blog blog = BlogMappers.mapBlogBodyToBlog(body,category);
        blog.setTags(tags);
        return blogRepository.save(blog);
    }
    public Boolean deleteBlog(Long id){
        Blog blog = blogRepository.findBlogById(id).orElseThrow(()-> new ResourceNotFoundException(String.format("The blog with id %s does not exist.", id.toString())));
        blogRepository.delete(blog);
        return true;
    }
    public List<Blog> searchBlogs(String searchTerm){
        return blogRepository.findByTitleContainingOrContentContaining(searchTerm, searchTerm);
    }
    @Transactional
    public boolean addComment(Long blogId, CommentBody comment){
        Blog blog = blogRepository
                .findBlogById(blogId)
                .orElseThrow(()->
                new ResourceNotFoundException(
                        String.format("The blog with id %s does not exist.", blogId.toString()
                        )
                )
                );
        commentService.addNewComment(BlogMappers.mapCommentBodyToComment(comment, blog));
        blogRepository.save(blog);
        return true;
    }
    public boolean removeComment(Long blogId, Long commentId){
        blogRepository.findBlogById(blogId).orElseThrow(()-> new ResourceNotFoundException(
                String.format("The blog with id %s does not exist.", blogId.toString())
        ));
        return commentService.deleteComment(commentId);
    }
    public boolean addTag(Long blogId,TagBody tag){
        Blog blog = blogRepository.findBlogById(blogId).orElseThrow(()-> new ResourceNotFoundException(
                String.format("The blog with id %s does not exist.", blogId.toString())
        ));
        TagBody tagBody = tagService.createTag(tag);
        Tag tag1 = tagService.getTagById(tagBody.getId());
        List<Tag> tagList = blog.getTags()
                .stream()
                .filter(tag2 -> !Objects.equals(tag2.getTagName(), tag1.getTagName()))
                .collect(Collectors.toList());
        tagList.add(tag1);
        blog.setTags(tagList);
        blogRepository.save(blog);
        return true;
    }
    public boolean removeTag(Long blogId, Long tagId, User user){
        Blog blog = blogRepository.findBlogById(blogId).orElseThrow(()-> new ResourceNotFoundException(
                String.format("The blog with id %s does not exist.", blogId.toString())
        ));
        List<Tag> tagList =blog
                .getTags()
                .stream().filter(tag -> tag.getId() != tagId)
                .collect(Collectors.toList());
        blog.setTags(tagList);
        blog.setUpdatedBy(user);
        blogRepository.save(blog);
        return true;
    }
    public boolean bulkAddBlogs(List<BlogBody> blogs){
        blogs.forEach(blogBody -> {
            Blog blog = createBlog(blogBody);
            List<CommentBody> comments = blogBody.getComments();
            comments.forEach(commentBody -> {
                Comment newComment = BlogMappers.mapCommentBodyToComment(commentBody, blog);
                commentService.addNewComment(newComment);
            });
        });
        return true;
    }
    public boolean publishBlog(Long id, User user){
        Blog blog = blogRepository.findBlogById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("This blog does not exist: (%s) ", id)));
        blog.setPublished(true);
        blog.setUpdatedBy(user);
        blogRepository.save(blog);
        return true;
    }
   public Boolean unPublishBlog(Long id, User user){
       Blog blog = blogRepository.findBlogById(id)
               .orElseThrow(() -> new ResourceNotFoundException(String.format("This blog does not exist: (%s) ", id)));
       blog.setPublished(false);
       blog.setUpdatedBy(user);
       blogRepository.save(blog);
       return true;
   }
   public Boolean archiveBlog(Long id, User user){
       Blog blog = blogRepository.findBlogById(id)
               .orElseThrow(() -> new ResourceNotFoundException(String.format("This blog does not exist: (%s) ", id)));
       blog.setArchived(true);
       blog.setArchivedOn(new Date());
       blog.setUpdatedBy(user);
       blogRepository.save(blog);
       return true;
   }
    public Boolean unArchiveBlog(Long id, User user){
        Blog blog = blogRepository.findBlogById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("This blog does not exist: (%s) ", id)));
        blog.setArchived(false);
        blog.setUpdatedBy(user);
        blog.setArchivedOn(new Date());
        blogRepository.save(blog);
        return true;
    }
    public List<BlogBody> getBlogsByCategory (String category){
        Category category1 = categoryService.getCategoryByName(category);
        List<Blog> blogs = blogRepository.findBlogsByCategory(category1);
        List<BlogBody> blogBodies = new ArrayList<>();
        blogs.forEach(blog -> {
            List<Comment> comments = commentService.getCommentsByBlog(blog);
            BlogBody body = BlogMappers.mapBlogToBlogBody(blog, comments);
            blogBodies.add(body);
        });
        return blogBodies;
    }
    public User getUserById(Long id){
        return userRepository.findUserById(id).orElseThrow(() ->new ResourceNotFoundException("Author does not exist"));
    }
}
// TODO: Add relationship to Blog to tags, comments, to fetch with a single call
// TODO: Employ mappers to convert POJO to Entities