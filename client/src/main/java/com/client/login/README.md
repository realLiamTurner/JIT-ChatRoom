# Login
## MainLauncher.java
此类继承javafx.application.Application抽象类,并实例化它的抽象方法start().
Application类是JavaFX应用的入口,通过在start()方法中实现UI控件的布局.

在main()方法中,调用launch()方法来启动应用.

所有的JavaFX主程序都需要继承Application类。
继承了Application类的子类必须重写start方法。start方法中可以进行UI控件的布局。当JavaFX程序启动的时候，会自动调用start方法。
mian函数中必须调用launch方法，该方法会启动JavaFX程序。
JavaFX程序用户界面的顶层是stage，代表窗体。stage中是scene，scene中可以包含各种UI控件和布局控件，共同组成用户UI。
当然，这只是一个入门的JavaFX程序，还看不出JavaFX技术的优点。

