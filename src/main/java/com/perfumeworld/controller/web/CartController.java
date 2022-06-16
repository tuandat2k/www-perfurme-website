package com.perfumeworld.controller.web;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.perfumeworld.dto.OrderDto;
import com.perfumeworld.dto.ProductDto;
import com.perfumeworld.dto.ProductOrderDto;
import com.perfumeworld.dto.SessionGioHang;
import com.perfumeworld.dto.SessionUser;
import com.perfumeworld.dto.UserDto;
import com.perfumeworld.interceptor.Auth;
import com.perfumeworld.interceptor.Auth.Role;
import com.perfumeworld.service.OrderService;
import com.perfumeworld.service.ProductOrderService;
import com.perfumeworld.service.ProductService;
import com.perfumeworld.service.UserService;



@Controller
public class CartController {
    @Autowired
    ProductService productService;

    @Autowired
    ProductOrderService productOrderService;

    @Autowired
    OrderService orderService;

    @Autowired
    UserService userService;

    @Autowired
    JavaMailSender mailSender;

    public static String userAccount = null;

    @RequestMapping(path = "/cart", method = RequestMethod.GET)
    public String viewCartProduct(ModelMap map, HttpSession session, @RequestParam(required = false) String message) {
        List<ProductDto> listSpDto = new ArrayList<ProductDto>();
        SessionGioHang gioHang = (SessionGioHang) session.getAttribute("gioHang");
        if (gioHang != null) {
            for (ProductOrderDto productOrderDto : gioHang.getGioHangs()) {
                ProductDto dto = productService.findProductByIdService(productOrderDto.getIdProduct());
                dto.setTotalItem(productOrderDto.getCount());
                dto.setTotalPrices(productOrderDto.getPrice());
                listSpDto.add(dto);
            }
        }
        map.addAttribute("message", message);
        map.addAttribute("orderdto", new OrderDto());
        map.addAttribute("productsCart", listSpDto);
        return "web/giohang";
    }

    @RequestMapping(path = "/api/v1/cart", method = RequestMethod.POST)
    @ResponseBody
    public String themSanPhamVaoGioHang(@RequestBody ProductOrderDto productOrderDto, HttpSession session) {
        boolean checkTonTai = false;
        int existProduct = -1;
        int vtExits = -1;
        long total = 0;
        int totalItem = 0;
        SessionGioHang gioHang = (SessionGioHang) session.getAttribute("gioHang");
        ProductDto productDto = productService.findProductByIdService(productOrderDto.getIdProduct());

        if (productDto.getCount() < productOrderDto.getCount()) {
            return "fail";
        }
        if (productOrderDto.getCount() < 1) {
            return "fail";
        }
        for (int i = 0; i < gioHang.getGioHangs().size(); i++) {
            if (productOrderDto.getIdProduct() == gioHang.getGioHangs().get(i).getIdProduct()) {
                checkTonTai = true;
                existProduct = gioHang.getGioHangs().get(i).getCount();
                vtExits = i;
                break;
            }
        }

        if (checkTonTai) {
            productOrderDto.setCount(productOrderDto.getCount() + existProduct);
            productOrderDto.setPrice(
                    (long) ((productOrderDto.getPrice() - productOrderDto.getPrice() * (productDto.getDiscount() / 100))
                            * productOrderDto.getCount()));
            gioHang.getGioHangs().set(vtExits, productOrderDto);
            gioHang.setGioHangs(gioHang.getGioHangs());
            for (ProductOrderDto dto : gioHang.getGioHangs()) {
                total += dto.getPrice();
                totalItem += dto.getCount();
            }
            NumberFormat nf = NumberFormat.getInstance(new Locale("en", "US"));
            gioHang.setTotalPrice(nf.format(total));
            gioHang.setTotalItem(totalItem);
        } else {
            productOrderDto.setPrice(
                    (long) ((productOrderDto.getPrice() - productOrderDto.getPrice() * (productDto.getDiscount() / 100))
                            * productOrderDto.getCount()));
            gioHang.getGioHangs().add(productOrderDto);
            gioHang.setGioHangs(gioHang.getGioHangs());

            for (ProductOrderDto dto : gioHang.getGioHangs()) {
                total += dto.getPrice();
                totalItem += dto.getCount();
            }
            NumberFormat nf = NumberFormat.getInstance(new Locale("en", "US"));
            gioHang.setTotalPrice(nf.format(total));
            gioHang.setTotalItem(totalItem);
        }

        return "success";
    }

    @RequestMapping(path = "/api/v1/cart/{idProduct}", method = RequestMethod.DELETE)
    @ResponseBody
    public String xoaSanPhamRaGioHang(@PathVariable int idProduct, HttpSession session) {
        SessionGioHang gioHang = (SessionGioHang) session.getAttribute("gioHang");
        ProductOrderDto spXoa = null;
        for (ProductOrderDto dto : gioHang.getGioHangs()) {
            if (dto.getIdProduct() == idProduct) {
                spXoa = dto;
                break;
            }
        }
        gioHang.getGioHangs().remove(spXoa);
        gioHang.setGioHangs(gioHang.getGioHangs());
        long total = 0;
        int totalItem = 0;
        for (ProductOrderDto dto : gioHang.getGioHangs()) {
            total += dto.getPrice();
            totalItem += dto.getCount();
        }
        NumberFormat nf = NumberFormat.getInstance(new Locale("en", "US"));
        gioHang.setTotalPrice(nf.format(total));
        gioHang.setTotalItem(totalItem);
        return "success";
    }

    @Auth(role = Role.LOGIN)
    @RequestMapping(path = "/order", method = RequestMethod.POST)
    public String insertOrder(ModelMap map, HttpSession session, HttpServletRequest request, @Validated @ModelAttribute("orderdto") OrderDto orderdto, BindingResult bindingResult) {
        SessionGioHang gioHang = (SessionGioHang) session.getAttribute("gioHang");
        if (bindingResult.hasErrors()) {
            List<ProductDto> listSpDto = new ArrayList<ProductDto>();
            if (gioHang != null) {
                for (ProductOrderDto productOrderDto : gioHang.getGioHangs()) {
                    ProductDto dto = productService.findProductByIdService(productOrderDto.getIdProduct());
                    dto.setTotalItem(productOrderDto.getCount());
                    dto.setTotalPrices(productOrderDto.getPrice());
                    listSpDto.add(dto);
                }

            }
            map.addAttribute("message", "");
            map.addAttribute("order", new OrderDto());
            map.addAttribute("productsCart", listSpDto);
            return "web/giohang";
        }
        if (gioHang.getGioHangs().size() <= 0) {
            return "redirect:/home";
        }

        try {

            NumberFormat nf = NumberFormat.getInstance(new Locale("en", "US"));
            StringBuilder sendText = new StringBuilder();
            String from = "toychildshop@gmail.com";
            List<ProductDto> listProduct = new ArrayList<ProductDto>();

            MimeMessage mail = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mail);

            SessionUser sessionUser = (SessionUser) session.getAttribute("sessionUser");
            UserDto userDtoMail = userService.findByIdUserService(sessionUser.getUserId());
            UserDto userDto = new UserDto();

            orderdto.setTotalMoney(Long.parseLong(gioHang.getTotalPrice().replace(",", "")));
            orderdto.setStatus(false);
            userDto.setId(sessionUser.getUserId());
            orderdto.setUserDto(userDto);

            orderdto = orderService.saveOderService(orderdto);

            //format mail
            sendText.append(
                    "<!DOCTYPE html PUBLIC \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" +
                            "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
                            "   <head>\n" +
                            "      <meta name=\"viewport content=\"width=device-width />\n" +
                            "      <meta http-equiv=\"Content-Type\" content=\"text/html;\" charset=UTF-8 />\n" +
                            "      <style> \n" +
                            "         * {\n" +
                            "         margin: 0;\n" +
                            "         font-family: Helvetica Neue, Helvetica, Arial, sans-serif;\n" +
                            "         box-sizing: border-box; \n" +
                            "         font-size: 14px;\n" +
                            "         }\n" +
                            "         img {\n" +
                            "         max-width: 100%;\n" +
                            "         } \n" +
                            "         body {\n" +
                            "         -webkit-font-smoothing: antialiased;\n" +
                            "         -webkit-text-size-adjust: none;\n" +
                            "         width: 100% !important;\n" +
                            "         height: 100%;\n" +
                            "         line-height: 1.6em;\n" +
                            "         }\n" +
                            "         table td {\n" +
                            "         vertical-align: top;\n" +
                            "         }\n" +
                            "         body {\n" +
                            "         background-color: #f6f6f6;\n" +
                            "         }\n" +
                            "         .body-wrap {\n" +
                            "         background-color: #f6f6f6;\n" +
                            "         width: 100%;\n" +
                            "         }\n" +
                            "         .container {\n" +
                            "         display: block !important;\n" +
                            "         max-width: 600px !important;\n" +
                            "         margin: 0 auto !important;\n" +
                            "         /* makes it centered */\n" +
                            "         clear: both !important;\n" +
                            "         }\n" +
                            "         .content {\n" +
                            "         max-width: 600px;\n" +
                            "         margin: 0 auto;\n" +
                            "         display: block;\n" +
                            "         padding: 20px;\n" +
                            "         }\n" +
                            "         .main {\n" +
                            "         background-color: #fff;\n" +
                            "         border: 1px solid #e9e9e9;\n" +
                            "         border-radius: 3px;\n" +
                            "         }\n" +
                            "         .content-wrap {\n" +
                            "         padding: 20px;\n" +
                            "         }\n" +
                            "         .content-block {\n" +
                            "         padding: 0 0 20px;\n" +
                            "         }\n" +
                            "         .last {\n" +
                            "         margin-bottom: 0;\n" +
                            "         }\n" +
                            "         .first {\n" +
                            "         margin-top: 0;\n" +
                            "         }\n" +
                            "         .aligncenter {\n" +
                            "         text-align: center;\n" +
                            "         }\n" +
                            "         .alignright {\n" +
                            "         text-align: right;\n" +
                            "         }\n" +
                            "         .alignleft {\n" +
                            "         text-align: left;\n" +
                            "         }\n" +
                            "         .clear {\n" +
                            "         clear: both;\n" +
                            "         }\n" +
                            "         .invoice {\n" +
                            "         width: 100%;\n" +
                            "         margin: 0;\n" +
                            "         text-align: left;\n" +
                            "         }\n" +
                            "         .invoice td {\n" +
                            "         padding: 5px 0;\n" +
                            "         }\n" +
                            "         .invoice .invoice-items {\n" +
                            "         width: 100%;\n" +
                            "         }\n" +
                            "         .invoice .invoice-items td {\n" +
                            "         border-top: #eee 1px solid;\n" +
                            "         }\n" +
                            "         .invoice .invoice-items .total td {\n" +
                            "         border-top: 2px solid #333;\n" +
                            "         border-bottom: 2px solid #333;\n" +
                            "         font-weight: 700;\n" +
                            "         }\n" +
                            "      </style>\n" +
                            "   </head>\n" +
                            "   <body itemscope itemtype=\"http://schema.org/EmailMessage\">\n" +
                            "      <table class=\"body-wrap\">\n" +
                            "      <tr>\n" +
                            "         <td></td>\n" +
                            "         <td class=\"containerwidth=\"600\">\n" +
                            "            <div class=\"content\">\n" +
                            "            <table class=\"mainwidth=\"100% cellpadding=\"0 cellspacing=\"0\"> \n" +
                            "      <tr>\n" +
                            "         <td class=\"content-wrap aligncenter\">\n" +
                            "            <table width=\"100% cellpadding=\"0 cellspacing=\"0\">\n" +
                            "      <tr>\n" +
                            "         <td class=\"content-block\"> \n" +
                            "            <h1 class=\"aligncenter \n" +
                            "            style=\"text-align: center;text-transform: uppercase;margin:0;font-size:14px;height: 260px;\"> \n" +
                            "            <img src=\"https://drive.google.com/uc?export=view&id=1_yeYUrkUsdrq9g6yx0MLntJjmDHqiszE\" alt=\"ToyZone\" \n" +
                            "               style=\"width:250px\" /> \n" +
                            "            </h1> \n" +
                            "         </td>\n" +
                            "      </tr>\n" +
                            "      <tr>\n" +
                            "         <td style=\"display:block\"> \n" +
                            "            <h2 class=\"aligncenter \n" +
                            "            style=\"text-align: center;font-weight: 500;text-transform: uppercase;font-size: 18px;color: #0a9ae8;margin: 0\"> \n" +
                            "            Xác nhận đặt hàng - Hóa đơn điện tử \n" +
                            "            </h2> \n" +
                            "         </td>\n" +
                            "         <td style=\"display:block;margin-top:20px;text-align: left\"> \n" +
                            "            Xin chào <b>" + userDtoMail.getFullName() + "</b>, Cảm ơn bạn đã mua hàng tại <b>ToyZone</b>\n" +
                            "         </td>\n" +
                            "      </tr>\n" +
                            "      <tr>\n" +
                            "         <td class=\"content-block aligncenter\">\n" +
                            "            <table class=\"invoice\">\n" +
                            "      <tr>\n" +
                            "         <td style=\"display: block\"><span \n" +
                            "            style=\"display: inline-block;width: 120px\">Số hóa đơn:" +
                            "            </span><b>" + orderdto.getId() + "</b> \n" +
                            "         </td>\n" +
                            "         <td style=\"display: block\"><span \n" +
                            "            style=\"display: inline-block;width: 120px\">Ngày đặt:\n" +
                            "            </span><b>" + orderdto.getCreatedDate() + "</b> \n" +
                            "         </td>\n" +
                            "         <td style=\"display: block\"><span \n" +
                            "            style=\"display: inline-block;width: 120px\">Ngư�?i đặt:" +
                            "            </span><b>" + userDtoMail.getFullName() + "</b> \n" +
                            "         </td>\n" +
                            "         <td style=\"display: block\"><span \n" +
                            "            style=\"display: inline-block;width: 120px\">Ngư�?i gửi:\n" +
                            "            </span><b>" + orderdto.getReceiver() + "</b> \n" +
                            "         </td>\n" +
                            "         <td style=\"display: block\"><span \n" +
                            "            style=\"display: inline-block;width: 120px\">�?ịa chỉ giao hàng:\n" +
                            "            </span><b>" + orderdto.getDeliveryAddress() + "</b> \n" +
                            "         </td>\n" +
                            "         <td style=\"display: block\"><span \n" +
                            "            style=\"display: inline-block;width: 120px\">SDT ngư�?i nhận:\n" +
                            "            </span><b>" + orderdto.getPhone() + "</b> \n" +
                            "         </td>\n" +
                            "         <td style=\"display: block\"><span \n" +
                            "            style=\"display: inline-block;width: 120px\">Nội dung:\n" +
                            "            </span><b>" + orderdto.getContent() + "</b> \n" +
                            "         </td>\n" +
                            "         <td style=\"display:block\">\n" +
                            "            <table class=\"invoice-items\" cellpadding=\"0\" cellspacing=\"0\">\n" +
                            "               <tr>\n" +
                            "                  <td style=\"text-align: center;padding-top:25px\"><a \n" +
                            "                     rel=\"noopener noreferrer\" target=\"_blank\" \n" +
                            "                     href=\"http://localhost:8080/toyZone/home\" \n" +
                            "                     style=\"display: inline-block;font-weight: 400;text-align: center;white-space: nowrap;vertical-align: middle;padding: .75rem 70px;font-size: 1rem;line-height: 1.5;border-radius: .25rem;background: #0070b6;color: #fff;text-decoration: none;\">Truy cập nhanh</a></td>\n" +
                            "               </tr>\n" +
                            "            </table>\n" +
                            "         </td>\n" +
                            "         <td style=\"display: block;\">\n" +
                            "            <table class=\"invoice-items\" cellpading=\"0\">\n" +
                            "      <tr>\n" +
                            "         <th style='width:3px;'>Mã</th>\n" +
                            "         <th>Tên Sản Phẩm</th>\n" +
                            "         <th>Giá</th>\n" +
                            "         <th>Giảm Giá</th>\n" +
                            "         <th>Số Lượng</th>\n" +
                            "         <th>Tổng Ti�?n</th>\n" +
                            "      </tr>\n"
            );

            //kiem tra so luong hang co phu hop voi kho hay ko
            for (ProductOrderDto po : gioHang.getGioHangs()) {
                ProductDto productDto = productService.findProductByIdService(po.getIdProduct());
                if (productDto.getCount() >= po.getCount()) {
                    productDto.setCount(productDto.getCount() - po.getCount());
                    listProduct.add(productDto);
                    sendText.append("<tr>");
                    sendText.append("<td>").append(productDto.getId().intValue()).append("</td>");
                    sendText.append("<td>").append(productDto.getName()).append("</td>");
                    sendText.append("<td>").append(nf.format(productDto.getPrice().longValue())).append("</td>");
                    sendText.append("<td>").append(productDto.getDiscount()).append("</td>");
                    sendText.append("<td>").append(po.getCount()).append("</td>");
                    sendText.append("<td>").append(nf.format(po.getPrice().longValue())).append("</td>");
                    sendText.append("</tr>");
                } else {
                    orderService.deleteOrderService(orderdto.getId());
                    map.addAttribute("message", "Sản phẩm " + productDto.getName() + " đã hết hàng");
                    return "redirect:/cart";
                }

            }
            sendText.append("</table>\n");
            sendText.append("<p style='color: red; font-weight: bold;'> Tổng ti�?n : ").append(gioHang.getTotalPrice()).append(" VND");
            sendText.append(
                    "</td>\n" +
                            "</tr> \n" +
                            "</table> \n" +
                            "</td> \n" +
                            "</tr> \n" +
                            "<tr>\n" +
                            "   <td \n" +
                            "      style=\"font-style: italic;font-size: 12px;padding: 0 0 20px 0;text-align: center\"> \n" +
                            "      Cảm ơn quý khách đã mua hàng tại shop \n" +
                            "      chúng tôi. M�?i thắc mắc vui lòng liên hệ tổng đài <b>0999999999</b> để được hỗ trợ. \n" +
                            "      <br />Xin chân thành cảm ơn \n" +
                            "   </td>\n" +
                            "</tr>\n" +
                            "</table> \n" +
                            "</td> \n" +
                            "</tr> \n" +
                            "</table> \n" +
                            "</div> \n" +
                            "</td> \n" +
                            "<td></td>\n" +
                            "</tr> \n" +
                            "</table> \n" +
                            "</body> \n" +
                            "</html>"
            );
            int i = 0;
            for (ProductDto pDto : listProduct) {
                gioHang.getGioHangs().get(i).setIdOrder(orderdto.getId());
                productOrderService.saveProductOderService(gioHang.getGioHangs().get(i));
                productService.saveProductService(pDto);
                i++;
            }

            helper.setFrom(from, from);
            helper.setTo(userDtoMail.getEmail());
            helper.setReplyTo(from, from);
            helper.setSubject("[ToyZone] - Hóa �?ơn �?iện Tử");
            helper.setText(sendText.toString(), true);
            mailSender.send(mail);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        session = request.getSession(false);
        session.removeAttribute("gioHang");
        return "redirect:/home";

    }
}
