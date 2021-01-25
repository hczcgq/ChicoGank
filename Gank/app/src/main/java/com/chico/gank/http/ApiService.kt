package com.chico.gank.http

import com.chico.gank.model.Article
import com.chico.gank.model.ArticleDetail
import com.chico.gank.model.Banner
import com.chico.gank.model.Result
import io.reactivex.Observable

import retrofit2.http.GET
import retrofit2.http.Path

/**
 * @Author: Chico
 * @Date: 2020/12/28
 * @Description:
 */
interface ApiService {

    @GET("api/v2/banners")
    fun getBanner(): Observable<Result<List<Banner>>>

    @GET("api/v2/hot/views/Article/{page}")
    fun getHot(@Path("page") page: Int): Observable<Result<List<Article>>>

    @GET("api/v2/data/category/{category}/type/{type}/page/{page}/count/10")
    fun getArticle(
        @Path("category") category: String,
        @Path("type") type: String,
        @Path("page") page: Int
    ): Observable<Result<List<Article>>>

    @GET("api/v2/post/{post_id}")
    fun getArticleDetail(@Path("post_id") post_id: String): Observable<Result<ArticleDetail>>

    @GET("api/v2/search/{key}/category/{category}/type/ALL/page/1/count/20")
    fun search(
        @Path("key") key: String,
        @Path("category") category: String
    ): Observable<Result<List<Article>>>
}