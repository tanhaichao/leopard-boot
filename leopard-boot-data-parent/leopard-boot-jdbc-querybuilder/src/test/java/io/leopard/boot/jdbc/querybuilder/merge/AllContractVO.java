package io.leopard.boot.jdbc.querybuilder.merge;

import java.util.Date;

/**
 * 所有合同
 * 
 * @author 谭海潮
 */
public class AllContractVO {

	/**
	 * 采购合同ID
	 */
	private String contractId;

	/**
	 * 采购合同编号
	 */
	private String contractNo;

	/**
	 * 合同合理
	 */
	private ContractType type;

	/**
	 * 甲方（公司）
	 */
	private long firstEnterpriseId;

	/**
	 * 乙方（承运商）
	 */
	private long secondEnterpriseId;

	/**
	 * 发布时间
	 */
	private Date posttime;

	public String getContractId() {
		return contractId;
	}

	public String getContractNo() {
		return contractNo;
	}

	public long getFirstEnterpriseId() {
		return firstEnterpriseId;
	}

	public Date getPosttime() {
		return posttime;
	}

	public long getSecondEnterpriseId() {
		return secondEnterpriseId;
	}

	public ContractType getType() {
		return type;
	}

	public void setContractId(String contractId) {
		this.contractId = contractId;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public void setFirstEnterpriseId(long firstEnterpriseId) {
		this.firstEnterpriseId = firstEnterpriseId;
	}

	public void setPosttime(Date posttime) {
		this.posttime = posttime;
	}

	public void setSecondEnterpriseId(long secondEnterpriseId) {
		this.secondEnterpriseId = secondEnterpriseId;
	}

	public void setType(ContractType type) {
		this.type = type;
	}
}