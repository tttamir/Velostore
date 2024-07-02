package com.velostore.controller.main;

import com.velostore.model.Ordering;
import com.velostore.model.StatProduct;
import com.velostore.model.User;
import com.velostore.model.enums.Category;
import com.velostore.model.enums.OrderingStatus;
import com.velostore.model.enums.Role;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Attributes extends Main {

    protected void addAttributes(Model model) {
        model.addAttribute("role", getUserRole());
    }

    protected void addAttributesOrdering(Model model, Long idBuyer) {
        addAttributes(model);
        User selectedBuyer = userRepo.getReferenceById(idBuyer);
        model.addAttribute("buyers", userRepo.findAllByRole(Role.BUYER));
        model.addAttribute("selectedBuyer", selectedBuyer.getId());
        List<Ordering> orderingList = selectedBuyer.getOrderingList();
        List<Ordering> res = new ArrayList<>();

        for (Ordering i : orderingList) if (i.getOrderingStatus() == OrderingStatus.NOT_CONFIRMED) res.add(i);
        for (Ordering i : orderingList) if (i.getOrderingStatus() == OrderingStatus.CONFIRMED) res.add(i);
        for (Ordering i : orderingList) if (i.getOrderingStatus() == OrderingStatus.SHIPPED) res.add(i);

        model.addAttribute("orderings", res);
    }

    protected void addAttributesCart(Model model) {
        addAttributes(model);
        model.addAttribute("carts", orderingRepo.findAllByUserAndOrderingStatus(getUser(), OrderingStatus.ISSUED));
    }

    protected void addAttributesIndex(Model model) {
        addAttributes(model);
        model.addAttribute("categories", Category.values());
        model.addAttribute("products", productRepo.findAll());
    }

    protected void addAttributesIndex(Model model, Category category) {
        addAttributes(model);
        model.addAttribute("categories", Category.values());
        model.addAttribute("selectedCategory", category);
        model.addAttribute("products", productRepo.findAllByCategory(category));
    }

    protected void addAttributesStat(Model model) {
        addAttributes(model);
        List<StatProduct> list = statProductRepo.findAll();
        for (StatProduct i : list) {
            i.setIncome(round(i.getIncome(), 2));
        }
        statProductRepo.saveAll(list);
        list.sort(Comparator.comparing(StatProduct::getIncome));
        Collections.reverse(list);
        double sum = 0;
        for (StatProduct i : list) sum += i.getIncome();
        model.addAttribute("sum", Main.round(sum, 2));
        model.addAttribute("stats", list);

    }

    protected void addAttributesProductAdd(Model model) {
        addAttributes(model);
        model.addAttribute("categories", Category.values());
    }

    protected void addAttributesProductEdit(Model model, Long id) {
        addAttributes(model);
        model.addAttribute("categories", Category.values());
        model.addAttribute("product", productRepo.getReferenceById(id));
    }

    protected void addAttributesProduct(Model model, Long id) {
        addAttributes(model);
        model.addAttribute("product", productRepo.getReferenceById(id));
    }

    protected void addAttributesUsers(Model model) {
        addAttributes(model);
        model.addAttribute("actual", getUser());
        model.addAttribute("users", userRepo.findAll());
        model.addAttribute("roles", Role.values());
    }
}
