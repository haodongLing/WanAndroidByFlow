package io.github.haodongling.lib.common.global

/**
 * Author: tangyuan
 * Time : 2021/8/17
 * Description: 路由表
 */
object BizConst {
    const val SECHEME="haodong://"
    const val HOST="mvvm"
    /*ARouter 路由*/
    /*登陆*/
    const val LOGIN = "/app/login"
    /*主页面*/
    const val MAIN = "/app/main"
    /*启动页*/
    const val SPLASH="/app/splash"
    /*文章详情页*/
    const val ACTIVITY_ARTICLE="/app/article"
    /*用户详情页*/
    const val ACTIVITY_USERPAGE="/app/userpage"

    const val ACTIVITY_SEARCH="/app/search"
    /*体系*/
    const val ACTIVITY_KNOWLEDGE_ARTICLE="/app/knowledge_article"


    /*==============navigaton 路由地址==================*/
    const val FRAGMENT_SOFA="main/tabs/sofa"
    const val FRAGMENT_HOME="main/tabs/home"
    const val FRAGMENT_MINE="main/tabs/mine"
    const val FRAGMENT_KNOWLEDGE_NAV="main/tabs/knowledgeNav"
    /*--------------------------------------*/
    const val COLLECT_ARTICLE="/lg/collect/{id}/json" // 收藏文章
    const val COLLECT_URL="lg/collect/addtool/json"  // 收藏url
    const val UNCOLLECT_URL="lg/collect/deletetool/json" // 取消收藏url
}