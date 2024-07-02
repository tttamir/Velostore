package com.velostore.controller;

import com.velostore.controller.main.Attributes;
import com.velostore.model.Ordering;
import com.velostore.model.StatProduct;
import com.velostore.model.Product;
import com.velostore.model.User;
import com.velostore.model.enums.OrderingStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartCont extends Attributes {

    @GetMapping
    public String Cart(Model model) {
        addAttributesCart(model);
        return "cart";
    }

    @PostMapping("/add/{idProduct}")
    public String CartAdd(@PathVariable Long idProduct, @RequestParam int quantity) {
        User user = getUser();
        List<Ordering> orderingList = user.getOrderingList();

        for (Ordering i : orderingList) {
            if (i.getProduct().getId().equals(idProduct) && i.getOrderingStatus() == OrderingStatus.ISSUED) {
                i.addQuantity(quantity);
                orderingRepo.save(i);
                return "redirect:/product/{idProduct}";
            }
        }

        Product product = productRepo.getReferenceById(idProduct);
        Ordering ordering = new Ordering();
        ordering.addProductAndUser(product, user);
        ordering.addQuantity(quantity);
        orderingRepo.save(ordering);
        return "redirect:/product/{idProduct}";
    }

    @PostMapping("/buy/{id}")
    public String CartBuy(Model model, @PathVariable Long id) {
        Ordering ordering = orderingRepo.getReferenceById(id);
        if (ordering.getQuantity() > ordering.getProduct().getQuantity()) {
            addAttributesCart(model);
            model.addAttribute("message", "Недостаточно инструментов!");
            return "cart";
        }
        StatProduct statProduct = ordering.getProduct().getStatProduct();
        statProduct.addIncome(ordering.getQuantity(), ordering.getSum());
        ordering.setOrderingStatus(OrderingStatus.NOT_CONFIRMED);
        orderingRepo.save(ordering);
        return "redirect:/cart";
    }

    @GetMapping("/buy/all")
    public String CartBuyAll(Model model) {
        User user = getUser();
        List<Ordering> orderingList = user.getOrderingList();
        for (Ordering i : orderingList) {
            if (i.getQuantity() > i.getProduct().getQuantity()) {
                addAttributesCart(model);
                model.addAttribute("message", "Недостаточно инструментов!");
                return "cart";
            }
        }
        for (Ordering i : orderingList) {
            if (i.getOrderingStatus() == OrderingStatus.ISSUED) {
                StatProduct statProduct = i.getProduct().getStatProduct();
                statProduct.addIncome(i.getQuantity(), i.getSum());
                i.setOrderingStatus(OrderingStatus.NOT_CONFIRMED);
            }
        }
        orderingRepo.saveAll(orderingList);
        return "redirect:/cart";
    }

    @GetMapping("/delete/{id}")
    public String CartDelete(@PathVariable Long id) {
        Ordering ordering = orderingRepo.getReferenceById(id);
        ordering.removeQuantity();
        User user = ordering.getUser();
        user.removeOrdering(ordering);
        userRepo.save(user);
        return "redirect:/cart";
    }

    @GetMapping("/delete/all")
    public String CartDeleteAll() {
        User user = getUser();
        List<Ordering> orderingList = user.getOrderingList();
        List<Ordering> list = new ArrayList<>();
        for (Ordering i : orderingList) {
            if (i.getOrderingStatus() == OrderingStatus.ISSUED) {
                list.add(i);
            }
        }
        for (Ordering i : list) {
            i.removeQuantity();
            orderingRepo.save(i);
            user.removeOrdering(i);
        }
        userRepo.save(user);
        return "redirect:/cart";
    }
}
