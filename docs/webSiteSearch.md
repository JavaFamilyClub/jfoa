# 站内搜索解决方案: Gulp + Lucene

> 类似 [jfoa: https://github.com/JavaFamilyClub/jfoa](https://github.com/JavaFamilyClub/jfoa), 当项目功能越来越多, 
> 大部分站点都会提供站内搜索以方便用户能够快速定位到想要跳转的页面, 或者一些搜索引擎都必然需要一些全文检索的技术. jfoa 项目目前
> 可预见后台管理页面以后势必功能点较多, 因此便需要站内搜索的支持.

> 目前绝大多数项目都采用前后端分离的模式, 那么 Server 端如何确定前端页面路由地址和用户搜索关键字的对应关系? 

> 目前就 Java 而言, 全文检索技术基本都是基于 Lucene(无论是 Solr, 还是 Elasticsearch 根本原理都是基于 Lucene), 
