package lotto

import lotto.domain.LottoMoney
import lotto.domain.LottoNumber
import lotto.domain.LottoNumbers
import lotto.domain.LottoStatistics
import lotto.domain.LottoTicketCount
import lotto.domain.LottoTickets
import lotto.domain.LottoTicketsCount
import lotto.domain.LottoTicketsFactory
import lotto.domain.WinningNumbers
import lotto.ui.ConsoleInputView
import lotto.ui.ConsoleOutputView
import lotto.ui.dto.LottoStatisticsDto
import lotto.ui.dto.LottoTicketsCountDto
import lotto.ui.dto.LottoTicketsDto

fun main() {
    val tickets = buyTickets()
    val winning = getWinning()
    printResult(tickets, winning)
}

private fun buyTickets(): LottoTickets {
    val money = LottoMoney(ConsoleInputView.getBuyAmount())

    val manualCount = LottoTicketCount(ConsoleInputView.getManualLottoCount())
    val count = LottoTicketsCount.of(manualCount, money)
    val manualTickets = ConsoleInputView.getManualLottoTickets(count.manual.value)
    ConsoleOutputView.printTicketCount(LottoTicketsCountDto(count))

    return getTickets(manualTickets, count.auto)
}

private fun getTickets(inputs: List<String>, autoCount: LottoTicketCount): LottoTickets {
    val manualTickets = LottoTicketsFactory.convertToTickets(inputs)
    val autoTickets = LottoTicketsFactory.create(autoCount)
    val tickets = manualTickets + autoTickets
    ConsoleOutputView.printLottoTickets(LottoTicketsDto(tickets))

    return tickets
}

private fun getWinning(): WinningNumbers {
    val winning = LottoNumbers(ConsoleInputView.getWinning())
    val bonus = LottoNumber.valueOf(ConsoleInputView.getBonus())
    return WinningNumbers(winning, bonus)
}

private fun printResult(tickets: LottoTickets, winning: WinningNumbers) {
    val statistics = LottoStatistics.from(tickets, winning)
    val result = statistics.countByRanking
    val revenue = statistics.revenue
    ConsoleOutputView.printStatistics(LottoStatisticsDto(result, revenue))
}