package com.focus.action.core.vo;

public class TreeModel {

	private Long id;
	private Long pId;
	private String name;
	private boolean open;
	private boolean drag;
	private boolean dropRoot;
	private String url;
	private String target;

	public TreeModel() {
		// TODO Auto-generated constructor stub
	}



	public TreeModel(Long id, Long pId, String name, boolean open, String url,
			String target) {
		super();
		this.id = id;
		this.pId = pId;
		this.name = name;
		this.open = open;
		this.url = url;
		this.target = target;
	}



	public TreeModel(Long id, Long pId, String name, boolean open,
			boolean drag, boolean dropRoot) {
		super();
		this.id = id;
		this.pId = pId;
		this.name = name;
		this.open = open;
		this.drag = drag;
		this.dropRoot = dropRoot;
	}



	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getpId() {
		return pId;
	}

	public void setpId(Long pId) {
		this.pId = pId;
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	public boolean isDrag() {
		return drag;
	}

	public void setDrag(boolean drag) {
		this.drag = drag;
	}

	public boolean isDropRoot() {
		return dropRoot;
	}

	public void setDropRoot(boolean dropRoot) {
		this.dropRoot = dropRoot;
	}



	public String getUrl() {
		return url;
	}



	public void setUrl(String url) {
		this.url = url;
	}



	public String getTarget() {
		return target;
	}



	public void setTarget(String target) {
		this.target = target;
	}

	


}
