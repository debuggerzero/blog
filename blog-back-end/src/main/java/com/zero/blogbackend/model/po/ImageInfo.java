package com.zero.blogbackend.model.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 图片信息
 *
 * @author ZERO
 * @date 2023/2/3
 */
@TableName("storage_image")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageInfo {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("image_name")
    private String imageName;

    @TableField("image_path")
    private String imagePath;

    @TableField("image_hash")
    private String imageHash;

    @TableField("create_user")
    private Integer createUser;

    @TableField("create_time")
    private String createTime;

}
