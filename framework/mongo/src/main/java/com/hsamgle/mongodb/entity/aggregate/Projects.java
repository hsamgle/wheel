package com.hsamgle.mongodb.entity.aggregate;

import org.springframework.util.StringUtils;

import java.util.Arrays;

/**
 * @author 黄先国 | huangxg@sondon.net
 * @PackageName cn.com.dplus.mongodb.entity
 * @ClassName
 * @Description
 * @date 2018/3/9 8:35
 * @company 广州讯动网络科技有限公司
 */
public final class Projects extends AggregateOptions {

    /** 集合的字段名  */
    private String source;

    /** 更改名称 */
    private String property;

    /** 是否输出字段,默认是输出的 */
    private Boolean request = true;

    private Projects[] projects;

    private Expression expression;

    private String requestFields;

    private Projects( ) {}

    public Projects(String source, String property ) {
        this.source = source;
        this.property = property;
    }
    public Projects(String source, String property,Expression expression) {
        this.source = source;
        this.property = property;
        this.expression = expression;
    }

    public Projects(String source, Boolean request) {
        this.source = source;
        this.request = request;
    }

	public Projects(Projects[] projects) {
		this.projects = projects;
		super.setType(Type.PROJECT);
	}

	public Projects(String requestFields) throws Exception {
    	if(StringUtils.isEmpty(requestFields)){
			throw new Exception("需要指定字段");
	    }
		String[] fields = requestFields.split(",");
    	this.projects = new Projects[fields.length];
		for (int i = 0; i < fields.length; i++) {
			projects[i] = new Projects(fields[i],true);
		}
	}

	public String getSource ( ) {
        return source;
    }

    public String getProperty ( ) {
        return property;
    }

    public Boolean getRequest ( ) {
        return request;
    }

	public Projects[] getProjects() {
		return projects;
	}

	public Expression getExpression() {
		return expression;
	}

	public enum Expression{

		dateToString

	}

	@Override
	public String toString() {
		return "Projects{" +
				"source='" + source + '\'' +
				", property='" + property + '\'' +
				", request=" + request +
				", projects=" + Arrays.toString(projects) +
				", expression=" + expression +
				'}';
	}
}
