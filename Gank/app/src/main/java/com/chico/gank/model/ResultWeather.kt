package com.chico.gank.model

import java.io.Serializable

/**
 * @ClassName: Result
 * @Description: 返回结果通用类
 * @Author: Chico
 * @Date: 2019/12/25 18:25
 */
open class ResultWeather<T>(var error_code: Int,
                            var reason: String,
                            var result: T) : Serializable