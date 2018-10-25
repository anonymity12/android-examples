package nisrulz.github.sample.awesomelib;

import retrofit2.Retrofit;

public class AwesomeLibMain {

  public boolean init() {

    /* When retrofit is added as implementation in the app module's build.gradle , below will be a valid statement
     * and Retrofit class will exist on the classpath when this module is compiled. If the
     * implementation line is removed from app module's build.gradle
     * below will become invalid statement as Retrofit class will not exist on the classpath  when
     * this module is compiled.
     *
     * Having set Retrofit as compileOnly dependency for the awesomelib module, makes it dependent on （？？？）
     * app module's dependency graph in terms of availability  of Retrofit class in the classpath
     * after compilation
     */
    /*
    当retrofit 被添加到 awesome lib 的build.gradle 之后, 下面的语句才有效，并且一个Retrofit 类会在class path 中可见。
    如果没有 retrofit 依赖在 build.gradle  的mark1 处， 那么，语句无效
    
    Retrofit 仅仅是 compileOnly 的话，awesome lib 就是对 app 组件是独立的。？？？
    */
    Retrofit retrofit = new Retrofit.Builder().baseUrl("https://swapi.co/api/").build();

    return hasRetrofitOnClasspath();
  }

  private boolean hasRetrofitOnClasspath() {
    try {
      Class.forName("retrofit2.Retrofit");
      return true;
    } catch (ClassNotFoundException ex) {
      ex.printStackTrace();
    }
    return false;
  }
}
