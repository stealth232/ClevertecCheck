package ru.clevertec.check.main;

import ru.clevertec.check.creator.OrderCreator;
import ru.clevertec.check.creator.impl.OrderCreatorImpl;
import ru.clevertec.check.entity.*;
import ru.clevertec.check.entity.impl.CheckImpl;
import ru.clevertec.check.entity.impl.Product;
import ru.clevertec.check.exception.ProductException;
import ru.clevertec.check.myLinkedList.MyLinked;
import ru.clevertec.check.myLinkedList.impl.MyLinkedList;
import ru.clevertec.check.parser.ArgParser;
import ru.clevertec.check.parser.impl.ArgsParserImpl;
import ru.clevertec.check.proxy.ProxyFactory;
import java.util.*;

public class Main {

     public static void main(String[] args) throws ProductException {
          args = new String[]{"5-40", "1-70", "2-120", "3-100", "4-100", "3-35", "4-7"};
          //args = new String[]{"src\\main\\resources\\file.txt"};

          MyLinkedList<Product> products = new MyLinkedList<>();
          MyLinked<Product> productsProxy = (MyLinked<Product>) ProxyFactory.doProxy(products);
          productsProxy.add(new Bounty());
          productsProxy.add(new Snickers());
          productsProxy.add(new Nuts());
          productsProxy.add(new Mars());
          productsProxy.add(new Twix());

          ArgsParserImpl argParser = new ArgsParserImpl();
          OrderCreatorImpl orderCreator = new OrderCreatorImpl();
          ArgParser argParserProxy = (ArgParser) ProxyFactory.doProxy(argParser);
          OrderCreator orderCreatorProxy = (OrderCreator) ProxyFactory.doProxy(orderCreator);
          MyLinkedList<String> list = argParserProxy.parsParams(args);
          Map<String, Integer> map = orderCreatorProxy.order(list);

          CheckImpl check = new CheckImpl(map);
          Check checkProxy = (Check) ProxyFactory.doProxy(check);
          StringBuilder sb = checkProxy.showCheck(products);
          System.out.println(sb);
          checkProxy.printCheck(sb);
          checkProxy.printPDFCheck(sb);

     }
}
