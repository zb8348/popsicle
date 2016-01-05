package com.pfw.popsicle.security.vo;

import java.util.List;

import com.pfw.popsicle.security.entity.Resource;
import com.pfw.popsicle.vo.AbsJSonVO;


public class ResourceTreeVO extends AbsJSonVO{

	private static final long serialVersionUID = -8045800094949352496L;
	private Resource node;
	private List<ResourceTreeVO> children;
	public Resource getNode() {
		return node;
	}
	public void setNode(Resource node) {
		this.node = node;
	}
	public List<ResourceTreeVO> getChildren() {
		return children;
	}
	public void setChildren(List<ResourceTreeVO> children) {
		this.children = children;
	}
}
