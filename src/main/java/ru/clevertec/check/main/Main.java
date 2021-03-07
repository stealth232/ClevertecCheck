package ru.clevertec.check.main;

import ru.clevertec.check.dao.Repository;
import ru.clevertec.check.entities.parameters.ProductParameters;
import ru.clevertec.check.exception.ProductException;
import ru.clevertec.check.observer.entity.State;
import ru.clevertec.check.observer.listeners.Consoler;
import ru.clevertec.check.observer.listeners.Emailer;
import ru.clevertec.check.service.Check;
import ru.clevertec.check.service.impl.CheckImpl;
import ru.clevertec.check.utils.creator.OrderCreator;
import ru.clevertec.check.utils.creator.impl.OrderCreatorImpl;
import ru.clevertec.check.utils.parser.ArgsParser;
import ru.clevertec.check.utils.parser.impl.ArgsParserImpl;
import ru.clevertec.check.utils.proxy.ProxyFactory;

import java.util.List;

public class Main {

    public static void main(String[] args) throws ProductException {
        args = new String[]{"1-40", "2-70", "3-120", "4-100", "5-100", "6-35", "6-35", "card-345"};
        // args = new String[]{"src\\main\\resources\\file.txt"};
        List<ProductParameters> products = Repository.getProductList();
        ArgsParser argsParser = (ArgsParser) ProxyFactory.doProxy(new ArgsParserImpl());
        OrderCreator orderCreator = (OrderCreator) ProxyFactory.doProxy(new OrderCreatorImpl());
        List<String> list = argsParser.parsParams(args);
        Check check = new CheckImpl(orderCreator.makeOrder(list));
        Check checkProxy = (Check) ProxyFactory.doProxy(check);
        System.out.println(checkProxy.showCheck(products));
        check.getPublisher().subscribe(State.CHECK_WAS_PRINTED_IN_TXT, new Consoler());
        check.getPublisher().subscribe(State.CHECK_WAS_PRINTED_IN_PDF, new Emailer());
        checkProxy.printCheck(checkProxy.showCheck(products));
        checkProxy.printPDFCheck(checkProxy.pdfCheck(products));
    }
}
