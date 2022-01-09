package com.godzillajim.betterprogramming.domain.data;

import com.godzillajim.betterprogramming.domain.entities.blog.Blog;
import com.thedeanda.lorem.Lorem;
import com.thedeanda.lorem.LoremIpsum;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class BlogMockedData {
    private List<Blog> blogs;
    Lorem lorem = LoremIpsum.getInstance();
    private static BlogMockedData instance = null;
    public static BlogMockedData getInstance(){
        if(instance == null){
            instance = new BlogMockedData();
        }
        return instance;
    }
    public BlogMockedData(){
        blogs = new ArrayList<Blog>();
        for(long a=0; a < 10; a++ ){
            Blog blog = new Blog();
            blog.setTitle(lorem.getTitle(6));
            blog.setContent(lorem.getParagraphs(5,11));
            blog.setPrompt(lorem.getWords(20));
            blog.setSummary(lorem.getWords(12));
            blog.setId(a);
            blog.setCreatedBy(lorem.getName());
            blog.setCreatedDate(new Date());
            blog.setLastModifiedBy(lorem.getName());
            blog.setLastModified(new Date());
            blogs.add(blog);
        }
    }
    public List<Blog> fetchBlogs(){
        return blogs;
    }
    public Blog getBlogById(Long a){
        for(Blog blog: blogs){
            if(blog.getId()==a)
                return blog;
        }
        return null;
    }
    public List<Blog> searchBlogs(String searchTerm){
        return fetchBlogs().stream().filter((blog)->blog.getContent().toLowerCase().contains(searchTerm.toLowerCase())).collect(Collectors.toList());

    }
    public Blog createBlog(Long id, String title, String content){
        Blog newBlog = new Blog();
        newBlog.setId(id);
        newBlog.setTitle(title);
        newBlog.setContent(content);
        newBlog.setPrompt(lorem.getParagraphs(5,9));
        newBlog.setSummary(lorem.getParagraphs(5,9));
        newBlog.setCreatedDate(new Date());
        newBlog.setLastModified(new Date());
        newBlog.setCreatedBy(lorem.getNameFemale());
        newBlog.setLastModifiedBy(lorem.getName());
        blogs.add(newBlog);
        return newBlog;
    }
    public Blog updateBlog(Long id, String title, String content){
        for(Blog blog: blogs){
            if(blog.getId() == id){
                blog.setTitle(title);
                blog.setContent(content);
                return blog;
            }
        }
        return null;
    }
    public boolean deleteBlog(Long id){
        Blog blog = null;
        for(Blog b: blogs){
            if(b.getId() == id){
                blog = b;
            }
        }
        if(blog != null){
            blogs.remove(blog);
            return true;
        }
        return false;
    }
}
