package com.alorma.notifix.domain.model;

data class Contact(val id: String? = null,
                   val androidId: String?,
                   val name: String,
                   val userEmail: String? = null,
                   val userPhone: String? = null)