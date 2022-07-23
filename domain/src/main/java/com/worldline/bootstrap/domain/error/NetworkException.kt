package com.worldline.bootstrap.domain.error

import com.worldline.bootstrap.common.StatusCode
import java.io.IOException

class NetworkException(val code: StatusCode) : IOException()
