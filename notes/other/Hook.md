#### Hook资源

Android资源获取都是来源于Resources，Resources又是来源于AssetsManager，AssetsManager中有ApkAssets的数组存储Apk的路径，系统初始化时会加上Apk资源的路径，而我们自己就可以自己new 一个AssetsManager，里面存储插件Apk的路径即可，如果不进行修改，资源Id可能会与自己Apk的id重复，获取到的资源就不对了。

