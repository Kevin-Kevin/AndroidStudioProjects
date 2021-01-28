# Android 开发项目

## Alarm 

- 准备做一个 iOS 闹钟
- 定时器一定要有拨动指针的振动反馈手感
- 尚在学习自定义 view

![image](https://github.com/Kevin-Kevin/AndroidStudioProjects/blob/master/readme_picture/Alarm.jpg)



## AppWidgetDesktop 

- 一个桌面微件
- 显示实时网速以及闪存和内存剩余容量

![image](https://github.com/Kevin-Kevin/AndroidStudioProjects/blob/master/readme_picture/AppWidget.jpg)

## BatteryChangedRecord 

- 电池使用情况记录
- 安卓自带的只能看到曲线不能看到具体的数据
- 这个接收 Battery_changed 系统广播,每次将时间和电池情况记录到数据库
- 未完...

![image](https://github.com/Kevin-Kevin/AndroidStudioProjects/blob/master/readme_picture/BatteryRecord.jpg)

## netflow

- 网络使用情况
- 本来想查看每个 app 的具体实时网速
- 但是 一加 8t 不支持系统api trafficStats 查看每个 App 的情况
- 最后只做了App 每天的数据累计使用情况

![image](https://github.com/Kevin-Kevin/AndroidStudioProjects/blob/master/readme_picture/AppWidget.jpgnetflow.jpg)

## Reminder

- 准备做一个 iOS 提醒事项
- SQLite 数据库的使用还在学习中...

![image](https://github.com/Kevin-Kevin/AndroidStudioProjects/blob/master/readme_picture/AppWidget.jpgReminder.jpg)

## TestProject

- 学习 Android 开发的时候的练手项目

### AppFileIO

- App 内部私有目录文件读写测试

### BroadcastReceiverTest && BroadcastTest

- 广播发送与接收测试

### ListViewTest

- ListView 使用测试(难点在自定义Adapter)

### NetworkTest

- 网络 API 使用测试

### SharedPrefrences

- 共享参数使用测试(存储键值对信息,轻量级存储方案)

### SpinnerTest

- 下拉列表使用测试

### TouchEventTest

- 触摸事件测试
- 继承了一个  AppCompatTextView
- 实现 TextView 跟随手指移动
- 继承 TextView 会报错,因为谷歌之前改了,可以已经没有 TextView 了,xml 里面就算写 TextView 也会被编译器优化成  AppCompatTextView 

![image](https://github.com/Kevin-Kevin/AndroidStudioProjects/blob/master/readme_picture/AppWidget.jpgTouchEvent.png)