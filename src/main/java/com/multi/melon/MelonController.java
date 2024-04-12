package com.multi.melon;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

@RestController // @Controller + @ResponseBody
@Log4j
@RequiredArgsConstructor
public class MelonController {

	private final MelonService mService;
	
	@GetMapping("/melonChart")
	public ModelAndView showMelonChart() {
		Date today = new Date();
		String str = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(today);
		ModelAndView mv = new ModelAndView();
		mv.addObject("today", str);
		mv.setViewName("melon/chart");//뷰네임
		return mv;
	}//---------------------
	
	@GetMapping("/crawling")
	public ModelMap melonCrawling() throws Exception{
		int cnt = 0;
		log.info("--크롤링 시작---------");
		cnt = mService.collectMelonList();
		
		log.info("--크롤링 끝 ---------");
		ModelMap map = new ModelMap();
		map.put("result", cnt);
		return map;
	}//---------------------
	
	@GetMapping("/melonList")
	public List<MelonVO> melonList() throws Exception{
		return mService.getSongList();
	}//--------------------------

	@GetMapping("/aggregate")
	public List<SumVO> singerCount() throws Exception{
		return mService.getSingerSongCount();
	}//--------------------------
	
	
}
