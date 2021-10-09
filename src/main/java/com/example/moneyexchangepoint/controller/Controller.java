package com.example.moneyexchangepoint.controller;

import com.example.moneyexchangepoint.dto.MoneyExchangeReportForDay;
import com.example.moneyexchangepoint.dto.MoneyExchangeReportForPeriod;
import com.example.moneyexchangepoint.dto.MoneyExchangeResponse;
import com.example.moneyexchangepoint.entity.ExchangeRates;
import com.example.moneyexchangepoint.exception.ValidationException;
import com.example.moneyexchangepoint.service.ExchangeRatesServiceImpl;
import com.example.moneyexchangepoint.service.MoneyExchangeServiceReportsImpl;
import com.example.moneyexchangepoint.service.MoneyExchangeServiceWorkImpl;

import io.swagger.v3.oas.annotations.Operation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/exchange")
public class Controller {

    private final ExchangeRatesServiceImpl ratesService;
    private final MoneyExchangeServiceWorkImpl serviceWork;
    private final MoneyExchangeServiceReportsImpl serviceReports;

    @Autowired
    public Controller(ExchangeRatesServiceImpl ratesService,
                      MoneyExchangeServiceWorkImpl serviceWork,
                      MoneyExchangeServiceReportsImpl serviceReports) {
        this.ratesService = ratesService;
        this.serviceWork = serviceWork;
        this.serviceReports = serviceReports;
    }

    @GetMapping("/start")
    @Operation(summary = "Открытие рабочего дня", description = "В рамках данной операции приложение вызывает " +
            "API ПриватБанка и получает текущие обменные курсы по таким валютам, как доллар, евро и российский рубль. " +
            "После этого устанавливает собственные обменные курсы по указанным валютам и сохраняет их в БД. " +
            "По завершению этой операции приложение готово к работе.")
    public List<ExchangeRates> startWork() {
        return ratesService.saveExchangeRates();
    }


    @PostMapping("/saveRequest")
    @Operation(summary = "Сохранение заявки на обмен валюты", description = "Данная операция формирует и сохраняет в БД " +
            "заявку на обмен валюты. Входными данными для формирования завки являются: имя клиента, номер мобильного " +
            "телефона клиента, продаваемая валюта, сумма продаваемой валюты, покупаемая валюта. После завершения " +
            "данной операции сервис возвращает: уникальный номер заявки, присвоенный БД, сумму покупаемой валюты, " +
            "номер мобильного телефона клиента и ОТП-пароль (передается клиенту). Заявке в БД присваивается статус " +
            "\"Новая\". Продаваемая и покупаемая валюты задаются через код (гривна - UAH, доллар - USD, евро - EUR, " +
            "рубль - RUR). Код валюты вводится в верхнем регистре. Номер мобильного телефона клиента вводится в формате: " +
            "(050) 123-45-67.")
    public MoneyExchangeResponse saveExchangeRequest(@RequestParam String userName, String userPhone,
                                                     String saleMoney, float saleMoneyAmount, String buyMoney) throws ValidationException {
        return serviceWork.saveMoneyExchangeRequest(userName, userPhone, saleMoney, saleMoneyAmount, buyMoney);
    }


    @PostMapping("/confirmation")
    @Operation(summary = "Подтверждение заявки на обмен валюты", description = "Данная операция осуществляет " +
            "подтверждение обмена валюты. Клиент сообщает номер мобильного телефона и ОТП-пароль. Если пароль совпадает " +
            "с тем, что вернул предыдущий сервис (\"Сохранение заявки\") операция обмена считается успешной. Сервис " +
            "возвращает ответ \"Пароль верный\" и завке присваивается статус \"Выполнена\". В противном случае сервис " +
            "возвращает ответ \"Пароль неверный\" и завке присваивается статус \"Отменена\".")
    public String confirmationExchangeRequest(@RequestParam Integer id, String password) {
        return serviceWork.confirmationExchange(id, password);
    }


    @DeleteMapping ("/deleteRequest")
    @Operation(summary = "Удаление заявки на обмен валюты", description = "Данная операция позволяет удалить заявку на " +
            "обмен валюты по номеру телефона клиента если она имеет статус \"Новая\". Номер мобильного телефона клиента " +
            "вводится в формате: (050) 123-45-67.")
    public ResponseEntity<Void> deleteExchangeRequest(@RequestParam String userPhone) {
        serviceWork.deleteMoneyExchangeRequest(userPhone);
        return ResponseEntity.ok().build();
    }


    @GetMapping("/finish")
    @Operation(summary = "Окончание рабочего дня", description = "В рамках данной операции формируется отчет о работе " +
            "обменного пункта за день. Отчет содержит в себе данные по общему количеству проведенных операций (заявки " +
            "со статусом \"Выполнена\") по покупке/продаже валют, а также суммы покупки и продажи по каждой валюте за день.")
    public MoneyExchangeReportForDay finishWork() {
        return serviceReports.getReportForCurrentDay();
    }


    @GetMapping("/reportForPeriod")
    @Operation(summary = "Получение отчета за период", description = "Данная операция дает возможность получить информацию " +
            "по суммам покупки и продажи конкретной валюты за указанный период времени. Входными данными для этой операции " +
            "являются: тип валюты, дата начала и дата окончания периода времени. Тип валюты задается через код (гривна - UAH, " +
            "доллар - USD, евро - EUR, рубль - RUR). Код валюты вводится в верхнем регистре. Дата вводится в формате: " +
            "год-месяц-число (2021-09-30).")
    public MoneyExchangeReportForPeriod getReportForPeriod(@RequestParam String money, String firstDate, String lastDate) {
        return serviceReports.getReportForPeriod(money, firstDate, lastDate);
    }
}
