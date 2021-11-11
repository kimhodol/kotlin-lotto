package lotto

@JvmInline
value class LottoNumber(val value: Int) {
    init {
        require(value in 1..45) { "로또 번호는 1부터 45 사이의 숫자만 가능합니다." }
    }
}
