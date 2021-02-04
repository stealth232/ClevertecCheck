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
import ru.clevertec.check.utils.mylinkedlist.MyLinkedList;
import ru.clevertec.check.utils.parser.ArgsParser;
import ru.clevertec.check.utils.parser.impl.ArgsParserImpl;
import ru.clevertec.check.utils.proxy.ProxyFactory;

import java.util.List;

public class Main {

    public static void main(String[] args) throws ProductException {
        args = new String[]{"1-40", "2-70", "3-120", "4-100", "5-100", "6-35", "6-35", "card-4567"};
        //args = new String[]{"src\\main\\resources\\file.txt"};

        Repository repository = Repository.getInstance();
        repository.removeTable();
        repository.createTable();
        repository.fillRepository();
        List<ProductParameters> products = new MyLinkedList<>();
        List<ProductParameters> productsProxy = (List<ProductParameters>) ProxyFactory.doProxy(products);
        for (int i = 1; i < repository.getSize() + 1; i++) {
            productsProxy.add(repository.getId(i));
        }
        ArgsParser argsParser = (ArgsParser) ProxyFactory.doProxy(new ArgsParserImpl());
        OrderCreator orderCreator = (OrderCreator) ProxyFactory.doProxy(new OrderCreatorImpl());
        List<String> list = argsParser.parsParams(args);
        Check check = new CheckImpl(orderCreator.makeOrder(list));
        Check checkProxy = (Check) ProxyFactory.doProxy(check);
        StringBuilder stringBuilder = checkProxy.showCheck(products);
        StringBuilder stringBuilderPDF = checkProxy.pdfCheck(products);
        System.out.println(stringBuilder);
        check.getPublisher().subscribe(State.CHECK_WAS_PRINTED_IN_TXT, new Consoler());
        check.getPublisher().subscribe(State.CHECK_WAS_PRINTED_IN_PDF, new Emailer());
        checkProxy.printCheck(stringBuilder);
        checkProxy.printPDFCheck(stringBuilderPDF);
    }
}
