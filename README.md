#项目说明

演示站点：http://blog.ydemo.cn/

原作者项目：http://git.oschina.net/biezhi/tale

作者的项目看着挺简单的，做起来却不是那么一回事了。好多细节的处理让我感觉到和作者之间的差距巨大。于是，让自己的能力和项目对等，就简化了很多功能。也便于大家一起学习吧，毕竟我也是新手。现在项目整体的运行和原作者的基本一致。

1.开发的功能：

1. 用户管理：只面向个人用户，不提供对用户的CRUD，可以扩展。
1. 角色管理：安全框架必须，用户角色关联对应，可以进行扩展。
1. 文章发布：发布博文。
1. 友链管理：对网站挂载友情链接的管理。
1. 分类、标签管理：主要是给文章发布提供便捷。
1. 附件管理：使用七牛云对文章中要用到的图片文件统一进行管理。

由于选择的框架不同，对于原作者提供的一些功能暂时没有实现，一切从简，主要面对初学者。具体的我去掉的功能：评论这一块，系统设置中的站点设置，清除缓存，导出sql，配置插件，扩展主题模板。其余基本一致，可打包为jar war等多种形式直接运行。

2.项目框架

- 主框架：spring boot 1.5.2+ spring security4+jpa 
- 缓存：ehcache
- 后台管理模板：H-UI admin
- 前台主题模板：pingshu
- 数据库：mysql
- 模板引擎：thymeleaf
- 图片存储：七牛
- 前端框架在此不做说明，基本都用的差不多。

这里我感觉做的最有意义的事就是整合springboot 和security，扩展了JPA数据库访问，以及展示了如何通过七牛实现的云存储。

3.快速开始

码云地址：https://git.oschina.net/aper/base/

开发工具：idea。

运行环境：jdk1.8  maven3.0+

从码云导入项目至idea，修改resources文件下application.yml中mysql的配置信息，然后直接运行DemoApplication.java的main()方法。
如图所示：
![输入图片说明](http://on74nfn25.bkt.clouddn.com/peizhi.png "在这里输入图片标题")
![输入图片说明](http://on74nfn25.bkt.clouddn.com/b_run.png "在这里输入图片标题")

4.项目结构
![输入图片说明](http://on74nfn25.bkt.clouddn.com/b_jiegou.png "在这里输入图片标题")

Java源码在 src/main/java 中，

- 其中app包下的代码是业务开发中需求的bean dao controller等存放的地方。 
- 其中core是系统核心包，里头包括整合框架的一些配置信息，默认不要做任何改动。如进行开发从APP包开发即可。

src/main/resouces 文件夹中存放了框架配置文件和页面文件。

- 其中static文件下存放了系统所需的静态资源文件。
- 其中templates文件夹下存放本系统所有的页面信息。

#来几张开发后的美图
主页：
![输入图片说明](http://on74nfn25.bkt.clouddn.com/b_index.png "在这里输入图片标题")
详情页
![输入图片说明](http://on74nfn25.bkt.clouddn.com/b_detail.png "在这里输入图片标题")

后台主页：
![输入图片说明](http://on74nfn25.bkt.clouddn.com/b_admin_index.png "在这里输入图片标题")
友链
![输入图片说明](http://on74nfn25.bkt.clouddn.com/b_youlian.png "在这里输入图片标题")
附件：
![输入图片说明](http://on74nfn25.bkt.clouddn.com/b_attach.png?a=1 "在这里输入图片标题")
标签：
![输入图片说明](http://on74nfn25.bkt.clouddn.com/b-tags.png "在这里输入图片标题")