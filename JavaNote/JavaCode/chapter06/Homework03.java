3. 以下Java代码的输出结果为：1,3,5,7
int num = 1;
while (num < 10) {
	System.out.println(num);
	if (num > 5) {
		break;
	}
	num += 2;
}