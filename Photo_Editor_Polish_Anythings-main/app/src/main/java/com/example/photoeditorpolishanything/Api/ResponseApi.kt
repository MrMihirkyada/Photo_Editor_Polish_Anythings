package com.example.photoeditorpolishanything.Api

import com.google.gson.annotations.SerializedName

data class ResponseApi(

	@field:SerializedName("baseUrl")
	val baseUrl: String?,

	@field:SerializedName("data")
	val data: Dataesss?
)

data class ABoss(

	@field:SerializedName("Group")
	val group: Groupessss?
)

data class Dataesss(

	@field:SerializedName("A boss")
	val aBoss: ABoss?,

	@field:SerializedName("AESTHETIC")
	val aESTHETIC: AESTHETIC?
)

data class Groupessss(

	@field:SerializedName("subImageUrl")
	val subImageUrl: List<String?>?,

	@field:SerializedName("mainImageUrl")
	val mainImageUrl: List<String?>?
)

data class AESTHETIC(

	@field:SerializedName("Group")
	val group: Groupessss?
)
