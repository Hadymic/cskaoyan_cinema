package com.cskaoyan.cinema.rest.common.persistence.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author hadymic
 * @since 2019-10-18
 */
@TableName("mtime_promo_stock")
public class PromoStock extends Model<PromoStock> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId
    private Integer uuid;
    /**
     *  秒杀活动id
     */
    @TableField("promo_id")
    private Integer promoId;
    /**
     * 库存
     */
    private Integer stock;


    public Integer getUuid() {
        return uuid;
    }

    public void setUuid(Integer uuid) {
        this.uuid = uuid;
    }

    public Integer getPromoId() {
        return promoId;
    }

    public void setPromoId(Integer promoId) {
        this.promoId = promoId;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    @Override
    protected Serializable pkVal() {
        return this.uuid;
    }

    @Override
    public String toString() {
        return "PromoStock{" +
        "uuid=" + uuid +
        ", promoId=" + promoId +
        ", stock=" + stock +
        "}";
    }
}
