package com.reliable.yang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.reliable.yang.dao.mapper.ArticleBodyMapper;
import com.reliable.yang.dao.mapper.ArticleMapper;
import com.reliable.yang.dao.dos.Archives;
import com.reliable.yang.dao.mapper.ArticleTagMapper;
import com.reliable.yang.dao.pojo.Article;
import com.reliable.yang.dao.pojo.ArticleBody;
import com.reliable.yang.dao.pojo.SysUser;
import com.reliable.yang.service.*;
import com.reliable.yang.utils.UserThreadLocal;
import com.reliable.yang.vo.*;
import com.reliable.yang.vo.params.ArticleParam;
import org.joda.time.DateTime;
import com.reliable.yang.vo.params.PageParams;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 * @date 2022-06-20 16:52
 */
@SuppressWarnings("ALL")
@Service
public class ArticleServiceImpl implements ArticleService {
	@Autowired
	private ArticleMapper articleMapper;
	@Autowired
	private ArticleTagMapper articleTagMapper;

	@Autowired
	private TagService tagService;
	@Autowired
	private SysUserService sysUserService;

	@Autowired
	private CategoryService categoryService;

	// 首页列表 （标题、标签、作者）
	private List<ArticleVo> copyList(List<Article> records, boolean isTag, boolean isAuthor) {
		List<ArticleVo> articleVoList = new ArrayList<>();
		for (Article record : records) {
			articleVoList.add(copy(record, isTag, isAuthor,false,false));
		}
		return articleVoList;
	}
	// 重载 文章详情 多了两个参数 boolean isBody, boolean isCategory
	private List<ArticleVo> copyList(List<Article> records, boolean isTag, boolean isAuthor,
	                                 boolean isBody, boolean isCategory) {
		List<ArticleVo> articleVoList = new ArrayList<>();
		for (Article record : records) {
			articleVoList.add(copy(record, isTag, isAuthor,isBody,isCategory));
		}
		return articleVoList;
	}
	// 统一处理方法
	public ArticleVo copy(Article article, boolean isTag, boolean isAuthor,
	                      boolean isBody, boolean isCategory) {
		ArticleVo articleVo = new ArticleVo();
		articleVo.setId(String.valueOf(article.getId()));   // 防止精度问题 转字符串
		BeanUtils.copyProperties(article, articleVo);
		articleVo.setCreateDate(new DateTime(article.getCreateDate()).toString("yyyy-MM-dd HH:mm"));
		// 并不是所有的接口都需要标签
		if (isTag) {
			// 先拿到tag对应的ID 再用ID去查找到对应的标签名
			Long articleId = article.getId();
			articleVo.setTags(tagService.findTagsByArticleId(articleId));
		}
		// 并不是所有的接口都需要作者
		if (isAuthor) {
			// 先拿到作者ID 再用作者ID找到对应的昵称
			Long articleId = article.getAuthorId();
			SysUser sysUser = sysUserService.findSysUserById(articleId);
			UserVo userVo = new UserVo();
			userVo.setAvatar(sysUser.getAvatar());
			userVo.setNickname(sysUser.getNickname());
			userVo.setId(String.valueOf(sysUser.getId()));
			articleVo.setAuthor(userVo);
		}
		// 并不是所有的接口都需要文章内容
		if (isBody) {
			ArticleBodyVo articleBody = findArticleBody(article.getId());
			articleVo.setBody(articleBody);
		}
		// 并不是所有的接口都需要分类
		if (isCategory) {
			Long categoryId = article.getCategoryId();
			articleVo.setCategory(categoryService.findCategoryById(categoryId));
		}
		return articleVo;
	}

	//--------------------------------------------------------

	/**
	 * 导航栏文章归档的处理 不同的月份返回不同的文章列表
	 * @param pageParams
	 * @return
	 */
	@Override
	public Result listArticle(PageParams pageParams) {
		Page<Article> page = new Page<>(pageParams.getPage(),pageParams.getPageSize());
		IPage<Article> articleIPage = this.articleMapper.listArticle(
				page,
				pageParams.getCategoryId(),
				pageParams.getTagId(),
				pageParams.getYear(),
				pageParams.getMonth());
		return Result.success(copyList(articleIPage.getRecords(),true,true));
	}
	/*@Override
	public Result listArticle(PageParams pageParams) {
		*//**
		 * 1. 分页查询得到article表的数据
		 *//*

		System.out.println("页码： " + pageParams.getPage());
		System.out.println("每页大小： " + pageParams.getPageSize());
		Page<Article> page = new Page<Article>(pageParams.getPage(), pageParams.getPageSize());

		LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
		//查询文章的分类参数 加上分类id，判断不为空 加上分类条件
		if (pageParams.getCategoryId() != null) {
			queryWrapper.eq(Article::getCategoryId,pageParams.getCategoryId());
		}
		//查询文章的标签参数 加上分类id，判断不为空 加上分类条件
		List<Long> articleIdList = new ArrayList<>();
		if (pageParams.getTagId() != null){
			LambdaQueryWrapper<ArticleTag> articleTagLambdaQueryWrapper = new LambdaQueryWrapper<>();
			articleTagLambdaQueryWrapper.eq(ArticleTag::getTagId,pageParams.getTagId());
			List<ArticleTag> articleTags = articleTagMapper.selectList(articleTagLambdaQueryWrapper);
			for (ArticleTag articleTag : articleTags) {
				articleIdList.add(articleTag.getArticleId());
			}
			if (articleIdList.size() > 0){
				queryWrapper.in(Article::getId,articleIdList);
			}
		}
		// 先是否按置顶排序 再按照创建日期降序排列
		queryWrapper.orderByDesc(Article::getWeight, Article::getCreateDate);
		// 把条件放进去
		Page<Article> articlePage = articleMapper.selectPage(page, queryWrapper);

		List<Article> records = articlePage.getRecords();
		System.out.println("\n mybatis-plus 查询到" + records.size() + "条数据！");
		// 封装返回值
		List<ArticleVo> articleVoList = copyList(records, true, true);

		return Result.success(articleVoList);
	}*/

	/**
	 * 首页 最热文章
	 * @param pageParams
	 * @return
	 */
	@Override
	public Result hotArticle(int limit) {
		LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.orderByDesc(Article::getViewCounts);
		queryWrapper.select(Article::getId, Article::getTitle);
		queryWrapper.last("limit " + limit);
		//select id,title from article order by view_counts desc;
		List<Article> articles = articleMapper.selectList(queryWrapper);
		return Result.success(copyList(articles,false,false));
	}

	/**
	 * 首页 最新文章
	 * @param limit
	 * @return
	 */
	@Override
	public Result newArticles(int limit) {
		LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.orderByDesc(Article::getCreateDate);
		queryWrapper.select(Article::getId,Article::getTitle);
		queryWrapper.last("limit "+limit);
		//select id,title from article order by create_date desc limit 5
		List<Article> articles = articleMapper.selectList(queryWrapper);

		return Result.success(copyList(articles,false,false));
	}

	/**
	 * 首页 归档
	 * @return
	 */
	@Override
	public Result listArchives() {
		List<Archives> archivesList = articleMapper.listArchives();
		return Result.success(archivesList);
	}

	/**
	 * 根据标题拿到文章详情
	 * @param id
	 * @return
	 */
	@Autowired
	private ThreadService threadService;
	@Override
	public Result findArticleById(Long articleId) {
		/**
		 * 1. 根据id查询到文章信息
		 * 2. 根据 bodyid和 categoryid 查询到标签和正文
		 */
		Article article = this.articleMapper.selectById(articleId);
		ArticleVo articleVo = copy(article,true,true,true,true);
		/*
			查看文章阅读数增加的问题：线程池
			1. 查看文章和更新阅读数 性能问题
			2. 如果更新出错了 不能影响查看文章的功能
		 */
		threadService.updateViewCount(articleMapper,article);
		return Result.success(articleVo);
	}
	/**
	 * 查找文章正文 在copy函数里用
	 * @param articleId
	 * @return
	 */
	@Autowired
	private ArticleBodyMapper articleBodyMapper;
	private ArticleBodyVo findArticleBody(Long articleId) {
		LambdaQueryWrapper<ArticleBody> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(ArticleBody::getArticleId,articleId);
		ArticleBody articleBody = articleBodyMapper.selectOne(queryWrapper);
		ArticleBodyVo articleBodyVo = new ArticleBodyVo();
		articleBodyVo.setContent(articleBody.getContent());
		return articleBodyVo;
	}
	/**
	 *  发布文章
	 * 1. 构建文章内容
	 * 2. 作者ID（当前登录用户 使用local且要加入登录拦截器）
	 * 3. 标签（把标签加入标签文章关联表中）
	 * @param articleParam
	 * @return
	 */
	@Override
	@Transactional  // 加事务
	public Result publish(ArticleParam articleParam) {
		SysUser sysUser = UserThreadLocal.get();    //获取当前用户信息
		Article article = new Article();
		boolean isEdit = false;
		if (articleParam.getId() != null) {
			article =new Article();
			article.setId(articleParam.getId());
			article.setTitle(articleParam.getTitle());
			article.setSummary(articleParam.getSummary());
			article.setCategoryId(Long.parseLong(articleParam.getCategory().getId()));
		}else{
			article.setAuthorId(sysUser.getId());
			article.setCategoryId(Long.parseLong( articleParam.getCategory().getId()));
			article.setCreateDate(System.currentTimeMillis());
			article.setCommentCounts(0);
			article.setSummary(articleParam.getSummary());
			article.setTitle(articleParam.getTitle());
			article.setViewCounts(0);
			article.setWeight(Article.Article_Common);
			article.setBodyId(-1L);
			this.articleMapper.insert(article); //先插入 插入之后会生成一个ID
		}
		//tags
		List<TagVo> tags = articleParam.getTags();
		if (tags != null) {
			for (TagVo tag : tags) {
				ArticleTag articleTag = new ArticleTag();
				articleTag.setArticleId(article.getId());
				articleTag.setTagId(Long.parseLong(tag.getId()));
				this.articleTagMapper.insert(articleTag);
			}
		}

		ArticleBody articleBody = new ArticleBody();
		articleBody.setContent(articleParam.getBody().getContent());
		articleBody.setContentHtml(articleParam.getBody().getContentHtml());
		articleBody.setArticleId(article.getId());
		articleBodyMapper.insert(articleBody);

		article.setBodyId(articleBody.getId());
		articleMapper.updateById(article);
		ArticleVo articleVo = new ArticleVo();
		articleVo.setId(String.valueOf(article.getId()));

		return Result.success(articleVo);
	}
}