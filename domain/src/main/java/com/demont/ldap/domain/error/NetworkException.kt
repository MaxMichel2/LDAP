package com.demont.ldap.domain.error

import com.demont.ldap.common.StatusCode
import java.io.IOException

class NetworkException(val code: StatusCode) : IOException()
