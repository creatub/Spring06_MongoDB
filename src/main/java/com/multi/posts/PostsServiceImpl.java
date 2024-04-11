package com.multi.posts;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

@Service
public class PostsServiceImpl implements PostsService {

	@Inject
	private PostsDAO pDao;
	
	@Override
	public int insertPosts(PostsVO vo) {
		return pDao.insertPosts(vo);
	}

	@Override
	public List<PostsVO> listPosts() {
		// TODO Auto-generated method stub
		return pDao.listPosts();
	}

	@Override
	public PostsVO selectPosts(String id) {
		return pDao.selectPosts(id);
	}

	@Override
	public int deletePosts(String id) {
		return pDao.deletePosts(id);
	}

	@Override
	public int updatePosts(PostsVO vo) {
		return pDao.updatePosts(vo);
	}

}
