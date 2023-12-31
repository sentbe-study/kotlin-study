package day8

open class UserApiException(
    open val errorCode: ResultCode,
    override val message: String?,
    open val extraMessage: String? = null
) : RuntimeException(message)

data class SystemException(
    override val errorCode: ResultCode,
    override val message: String,
    override val extraMessage: String? = null
) : UserApiException(errorCode, message, extraMessage) {
   // TODO : constructor 추가 정의
}

@Suppress("unused")
enum class ResultCode(val message: String) {
    SUCCESS("ok"),
    INTERNAL_SERVER_ERROR("system error occurred!")
}

fun main() {

    try {
        val s = "abc"
        for (i in 0..s.lastIndex+1) {
            print(s[i] + 1)
        }
    } catch (e: Exception) {
        val customException = SystemException(ResultCode.INTERNAL_SERVER_ERROR, ResultCode.INTERNAL_SERVER_ERROR.toString(), e.message)
        println(customException.toString())
        assert("bcdSystemException(errorCode=INTERNAL_SERVER_ERROR, message=system error occurred!, extraMessage=String index out of range: 3)" == customException.toString())
        e.printStackTrace()
    }
}