package com.haodong.lib.common.model.bean;

import com.haodong.lib.common.BR;

import java.util.List;

import androidx.databinding.BaseObservable;

/**
 * Author: tangyuan
 * Time : 2021/8/20
 * Description:
 */
public class Article extends BaseObservable {
    public int id;
    public int Int;
    public String title;
    public int chapterId;
    public String chapterName;
    public String envelopePic;
    public String link;
    public String author;
    public String origin;
    public long publishTime;
    public int zan;
    public String desc;
    public int visible;
    public String niceDate;
    public String niceShareDate;
    public int courseId;
    public boolean collect;
    public String apkLink;
    public String projectLink;
    public int superChapterId;
    public String superChapterName;
    public int type;
    public boolean fresh;
    public int audit;
    public String prefix;
    public int selfVisible;
    public long shareDate;
    public String shareUser;
    public List<TagsBean> tags;
    //    val tags:Any, // Not sure
    public int userId;
    public boolean top;

    public void setCollect(boolean collect) {
        this.collect = collect;
        notifyPropertyChanged(BR._all);
    }

    public static class TagsBean {
        /**
         * name : 公众号
         * url : /wxarticle/list/410/1
         */

        public String name;
        public String url;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
