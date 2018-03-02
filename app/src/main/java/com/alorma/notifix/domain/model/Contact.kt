package com.alorma.notifix.domain.model;

data class Contact(val id: String,
                   val name: String,
                   val phone: String? = null,
                   val photo: String? = null)