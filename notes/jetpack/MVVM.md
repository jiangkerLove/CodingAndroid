MVVM架构可以将程序结构主要分为3个部分，Model是数据的模型部分，View是界面展示部分，而ViewModel是作为链接数据模型和界面展示的桥梁，从而实现使业务逻辑和界面展示分离的程序结构设计。而官方的Jetpack中的许多组件就是专门为MVVM而量身打造的。

官方的应用架构指南中有这么一副图，就可以很清晰的看出Jetpack相关组件在MVVM中充当的角色

![mvvm](../../images/jetpack/final-architecture.png)

数据根据情况选择来源于本地还是远程，View持有ViewModel的相关引用来显示数据以及提供功能实现，而ViewModel通过LiveData去驱动UI。这样可以使Activity或Fragment尽量保持精简，也杜绝ViewModel对Activity或Fragment的依赖，可以有效的避免很多生命周期相关的问题。

