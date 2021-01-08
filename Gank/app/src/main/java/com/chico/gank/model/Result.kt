package com.chico.gank.model

import java.io.Serializable

/**
 * @ClassName: Result
 * @Description: 返回结果通用类
 * @Author: Chico
 * @Date: 2019/12/25 18:25
 */
open class Result<T>(var code: Int,
                     var msg: String,
                     var data: T) : Serializable