package com.hsamgle.basic.constant;

/**
 * @author 黄先国 | huangxg@sondon.net
 * @PackageName com.hsamgle.basic.constant
 * @ClassName
 * @Description
 * @date 2018/11/16 14:47
 * @company 广州讯动网络科技有限公司
 */
public enum RequestMethodEnum {

	/** get */
	GET(1),
	/** post */
	POST(2),
	/** put */
	PUT(3),
	/** delete */
	DELETE(4);

	RequestMethodEnum(int key) {
		this.key = key;
	}
	public int key;

	public static int getKeyByName(String method){
		RequestMethodEnum[] values = values();
		for (RequestMethodEnum value : values) {
			if(value.toString().equalsIgnoreCase(method)){
				return value.key;
			}
		}
		return 1;
	}


	public static void main(String[] args) {

		System.out.println(getKeyByName("delete"));

	}
}
