package com.tanyang.twitter.control;

import com.tanyang.twitter.pojo.Comment;
import com.tanyang.twitter.pojo.Twitter;
import com.tanyang.twitter.pojo.User;
import com.tanyang.twitter.service.impl.CommentServiceImpl;
import com.tanyang.twitter.service.impl.TwitterServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class CommentControl {

    private Logger logger= LoggerFactory.getLogger(CommentControl.class);
    @Autowired
    private CommentServiceImpl commentServiceImpl;
    @Autowired
    private TwitterServiceImpl twitterServiceImpl;

    @RequestMapping("/addComment")
    @ResponseBody
    public boolean addComment(String content, String twitter, String parent,HttpSession session){
        User user=(User)session.getAttribute("user");
        Comment comment=new Comment();
        Twitter twi= twitterServiceImpl.getTwitterById(twitter);
        Comment parentComment=new Comment();
        if("".equals(parent)||parent==null){
            comment.setParent(null);
            comment.setContent(content);
        }else{
            parentComment=commentServiceImpl.getCommentById(parent);
            comment.setContent(content);
            comment.setParent(parentComment);
        }

        comment.setTwitter(twi);
        comment.setUser(user);
        return commentServiceImpl.addComment(comment);
    }

    @RequestMapping("/getComment")
    @ResponseBody
    public List<Comment> getCommentByTwitterId(String twitterid, Model model){
        logger.info("Twitterid:"+twitterid);
        List<Comment> list=null;
        Twitter twitter= twitterServiceImpl.getTwitterById(twitterid);
        list= commentServiceImpl.getCommentByTwitterId(twitter);
        for(Comment comment:list){
            if(comment.getParent()!=null&&!"".equals(comment.getParent())){
                comment.setContent("<b style='color:black'>回复&nbsp;"+comment.getParent().getFloor()+"楼</b>&nbsp;&nbsp;"+comment.getContent());
            }
        }
        model.addAttribute("commentlist",list);
        return list;
    }
}
