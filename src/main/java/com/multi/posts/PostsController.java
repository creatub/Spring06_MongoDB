package com.multi.posts;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.log4j.Log4j;

@Controller
@Log4j
public class PostsController{
	
	@Inject
	private PostsService pService;
	
	@GetMapping("/posts")
	public String postsForm() {
		return "posts/input";
	}
	
	@PostMapping(value="/posts", produces= {"application/json; charset=UTF-8"})
	@ResponseBody
	public Map<String, Object> postsInsert(PostsVO vo) {
//		log.info("vo: "+vo);
		System.out.println("vo: "+vo);
		Date d = new Date();
		String wdate = new SimpleDateFormat("yyyy-MM-dd").format(d);
		vo.setWdate(wdate);
		
		int n=pService.insertPosts(vo);
		Map<String, Object> map =new HashMap<>();
		map.put("result", n);
		return map;
	}//-----------------------------
	
	@GetMapping(value = "/postsAll", produces ="application/json; charset=utf-8")
    @ResponseBody
    public List<PostsVO> postList() {
        List<PostsVO> list = pService.listPosts();
        return list;
    }//----------------------
	
	@PostMapping(value="/postDelete", produces = "application/json; charset=utf-8")
	@ResponseBody
	public Map<String, Object> postsDelete(String id){
		int n = pService.deletePosts(id);
		Map<String, Object> map=new HashMap<>();
		map.put("result", n);
		return map;
	}//------------------------------
	
	@PostMapping(value="/postFind", produces = "application/json; charset=utf-8")
	@ResponseBody
	public PostsVO postFind(String id) {
		System.out.println("id: "+id);
		return pService.selectPosts(id);
	}//----------------
	
	@PostMapping(value="/postEdit", produces="application/json; charset=UTF-8")
	@ResponseBody
	public ModelMap postEditEnd(PostsVO vo) {
		System.out.println("vo: "+vo);
		int n = pService.updatePosts(vo);
		ModelMap map = new ModelMap();
		map.put("result", n);
		return map;
	}
	
	
}//////////////////////////////////
