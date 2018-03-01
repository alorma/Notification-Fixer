package com.alorma.notifix.domain.model;

data class Contact(val id: String,
                   val name: String,
                   val email: String? = null,
                   val phone: String? = null)