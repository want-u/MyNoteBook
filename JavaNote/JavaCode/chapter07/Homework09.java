public class Homework09 {
	public static void main(String[] args) {
		Music m = new Music("告白气球", 340);
		m.play();
		System.out.println(m.getInfo());

	}
}


// 9. 定义Music类
// 里面有音乐名name，音乐时长times属性
// 并由播放play功能和返回本身属性信息的功能方法getinfo
class Music {
	String name;
	int times;

	public Music(String name, int times) {
		this.name = name;
		this.times = times;
	}

	public void play() {
		System.out.println(name + "正在播放...");
	}

	public String getInfo() {
		return "音乐：" + name + " 时长：" + times + "s";
	}
}
