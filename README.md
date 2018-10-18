# IPC学习

## IPC介绍
   IPC是Inter-process Communication的缩写，进程间的通信或者跨进程通信。Android有自己的进程间通信方式，在Android中最有特色
的进程间通信方式就是Binder。 Android实现多进程的方法只有一个，给四大组件在androidMenifest中指定android：process。
所以我们无法给一个线程或一个实体类指定其运行时所在的线程。进程名以":"开头的进程属于当前应用的私有进程。android为每一个进程都
分配一个独立的虚拟机，不同的虚拟机在内存分配上有不同的地址空间，这就导致在不同的虚拟机中访问同一个类的对象会产生多份副本。
多进程模式中，不同进程的组件会拥有独立的虚拟机、application和内存空间。

## Binder
   Binder是Android中一种跨进程通信的方式，从Android Framework角度来说，Binder是ServiceManager连接各种
Manager（ActivityManager、WindowManager、。。。）和相应ManagerService的桥梁；从Android应用层来说，Binder是客户端
和服务端进行通信的媒介，当bindService的时候，服务端会返回一个包含了服务端业务调用的Binder对象，通过这个Binder对象，客户端
就可以获取服务端提供的服务或者数据，这里的服务包括普通服务和基于AIDL的服务。

## Android中的IPC方式
### 使用Bundle
  在Bundle中附加我们需要传输给远程进程的信息并通过Intent发出去。

### 使用文件共享
  数据存在sd卡中，实现数据共享。 使用SharedPreferences，sharedPreferences也属于文件的一种，但是由于系统对
它的读/写有一定的缓存策略，即在内存中会有一份SharedPreferences文件的缓存，因此在多进程模式下，系统对它的读写就变的不可靠，
面对高并发的读写访问时，SharedPreferences有很大的几率会丢失数据。

### 使用Messenger
   Messenger是一种轻量级的IPC方案，它的底层实现是AIDL。Messenger是以串行的方式处理客户端发来的消息，如果大量的消息同时
发送到服务端，服务端仍然只能一个个处理，如果有大量的并发请求，那么用Messenger就不太合适了。Messenger的作用主要是
传递消息，如果需要跨进程调用服务端的方法，Messenger就做不到了。

### 使用AIDL
- 创建aidl文件 声明接口和接口方法 aidl除了基本数据类型，其他类型的参数需要标上方向：in表示输入型参数，out表示输出型参数，
inout表示输入输出型参数。AIDL的包结构在服务端和客户端要保持一致，应为客户端需要反序列化服务端中和AIDL接口相关的所有类，
如果路径不同的话，就无法反序列化成功。
- Binder会把客户端传递过来的对象重新转化并生成一个新的对象，对象的跨进程传输的本质上都是反序列化的过程，这也是为什么AIDL中自
定义的对象都要事项parcelable接口的原因。
- 怎么实现解注册功能？
1 使用RemoteCallbackList,是系统专门提供用于删除跨进程listener的接口。跨进程传输的对象会在服务端生成不同的对象，但他们底层
的Binder对象是同一个。
2 客户端调用远程服务的方法，被调用的方法运行在服务端的Binder线程池中，同时客户端线程会被挂起，如果服务端方法执行比较耗时的话，
客户端线程是UI线程的话，就会导致客户端ANR。服务端的方法本身运行在服务端的Binder线程池中，所以服务端本省就可以执行大量的耗时操
作。同样服务端调用客户端的listener的方法时，被调用的方法也运行在客户端的Binder线程池中，要确保客户端方法运行在非UI线程中。
- 首先创建一个Service和一个AIDL接口，接着创建一个类继承自AIDL接口中的stub类并实现stub抽象方法，在Service的onBind方法中
返回这个类的对象，然后客户端就可以绑定服务端Service,建立连接后就可以访问服务端的方法了。

### Binder连接池
   将每个业务模块的Binder请求统一转发到远程Service中去执行，从而避免了重复创建Service的过程。 一个远程
Service调控多个Binder.

## 使用ContentProvider

## 使用Socket
 ﻿
- [老罗IPC 进程间通信机制Binder简要介绍和学习计划_链接](https://blog.csdn.net/luoshengyang/article/details/6618363)
- 《Android开发艺术探究》