package com.xiaokun.trainingpractice;

import java.util.List;

/**
 * <pre>
 *     作者   : 肖坤
 *     时间   : 2018/02/07
 *     描述   :
 *     版本   : 1.0
 * </pre>
 */
public class HomeEntity
{


    /**
     * HomeList : [{"title":"OkHttp监听加载进度"}]
     * Code : 0
     * Message : ok
     */

    private int Code;
    private String Message;
    private List<HomeListBean> HomeList;

    public int getCode()
    {
        return Code;
    }

    public void setCode(int Code)
    {
        this.Code = Code;
    }

    public String getMessage()
    {
        return Message;
    }

    public void setMessage(String Message)
    {
        this.Message = Message;
    }

    public List<HomeListBean> getHomeList()
    {
        return HomeList;
    }

    public void setHomeList(List<HomeListBean> HomeList)
    {
        this.HomeList = HomeList;
    }

    public static class HomeListBean
    {
        /**
         * title : OkHttp监听加载进度
         */

        private String title;

        public String getTitle()
        {
            return title;
        }

        public void setTitle(String title)
        {
            this.title = title;
        }
    }
}