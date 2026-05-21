package com.luxuryproductsholding.api.utils;

import com.luxuryproductsholding.api.dao.*;
import com.luxuryproductsholding.api.models.*;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;

@Component
public class Seeder {
    private ProductDAO productDAO;
    private ProductRepository productRepository;
    private final PasswordEncoder passwordEncoder;

    private OrderEntityDAO orderEntityDAO;
    private CustomUserRepository userRepository;
    private CustomUserDAO customUserDAO;
    private GiftcardEntityRepository giftcardEntityRepository;
    private RoleRepository roleRepository;
    private OrderEntityRepository orderEntityRepository;
    private ProductOrderRepository productOrderRepository;
    private ReturnRequestRepository returnRequestRepository;
    private ReturnItemRepository returnItemRepository;
    private CategoryRepository categoryRepository;

    public Seeder(ProductDAO productDAO, ProductRepository productRepository, PasswordEncoder passwordEncoder, OrderEntityDAO orderEntityDAO, CustomUserRepository userRepository, CustomUserDAO customUserDAO, GiftcardEntityRepository giftcardEntityRepository, RoleRepository roleRepository, OrderEntityRepository orderEntityRepository, ProductOrderRepository productOrderRepository, ReturnRequestRepository returnRequestRepository, ReturnItemRepository returnItemRepository, CategoryRepository categoryRepository) {
        this.productDAO = productDAO;
        this.productRepository = productRepository;
        this.passwordEncoder = passwordEncoder;
        this.orderEntityDAO = orderEntityDAO;
        this.userRepository = userRepository;
        this.customUserDAO = customUserDAO;
        this.giftcardEntityRepository = giftcardEntityRepository;
        this.roleRepository = roleRepository;
        this.orderEntityRepository = orderEntityRepository;
        this.productOrderRepository = productOrderRepository;
        this.returnRequestRepository = returnRequestRepository;
        this.returnItemRepository = returnItemRepository;
        this.categoryRepository = categoryRepository;
    }

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        this.seedProducts();
        this.seedOrders();
    }

    private void seedProducts() {
        GiftcardEntity giftcardEntity = new GiftcardEntity("11", 50, "Giftcard aangemaakt voor €50 – ideaal voor een kleine verrassing of attentie.");
        GiftcardEntity giftcardEntity1 = new GiftcardEntity("12", 100, "Giftcard van €100 aangemaakt – perfect voor verjaardagen of feestdagen.");
        GiftcardEntity giftcardEntity2 = new GiftcardEntity("13", 200, "Giftcard ter waarde van €200 – ruim budget voor luxe artikelen of meerdere aankopen.");
        GiftcardEntity giftcardEntity3 = new GiftcardEntity("14", 400, "Giftcard van €400 uitgegeven – ideaal voor een grotere aankoop of als groepscadeau.");
        GiftcardEntity giftcardEntity4 = new GiftcardEntity("15", 780, "Giftcard van €780 gegenereerd – premium waarde, geschikt voor high-end producten of exclusieve ervaringen.");

        this.giftcardEntityRepository.save(giftcardEntity);
        this.giftcardEntityRepository.save(giftcardEntity1);
        this.giftcardEntityRepository.save(giftcardEntity2);
        this.giftcardEntityRepository.save(giftcardEntity3);
        this.giftcardEntityRepository.save(giftcardEntity4);

        Category gloves = new Category("Gloves");
        Category champagne = new Category("Champagne");
        Category bag = new Category("Bags");
        Category liquor = new Category("Liquor");
        Category tiara = new Category("Tiaras");
        Category umbrella = new Category("Umbrella");
        Category jewelry = new Category("Jewelry");
        Category scarf = new Category("Scarfs");
        Category cardholder = new Category("Cardholders");
        Category beauty = new Category("Beauty");
        Category decoration = new Category("Decoration");
        Category shoes = new Category("Shoes");
        Category sport = new Category("Sport");
        categoryRepository.save(gloves);
        categoryRepository.save(champagne);
        categoryRepository.save(bag);
        categoryRepository.save(liquor);
        categoryRepository.save(tiara);
        categoryRepository.save(umbrella);
        categoryRepository.save(jewelry);
        categoryRepository.save(scarf);
        categoryRepository.save(cardholder);
        categoryRepository.save(beauty);
        categoryRepository.save(decoration);
        categoryRepository.save(shoes);
        categoryRepository.save(sport);

        Product parentProduct1 = productDAO.addProduct(new Product("Regalia Driving Gloves | Brown S",
                                "Regalia Driving Gloves crafted with meticulous attention to detail.\nBrown S.", 297.66,
                        gloves, "luxury_driving_glove_2f5861c2-cd14-4713-a32e-26453347ee44", 1, "brown"));
        productDAO.addProduct(new Product("Regalia Driving Gloves | Brown M",
                                "Regalia Driving Gloves crafted with meticulous attention to detail.\nBrown M.", 297.66,
                        gloves, "luxury_driving_glove_2f5861c2-cd14-4713-a32e-26453347ee44", 1, "brown",
                        parentProduct1.getProductNummer()));
        productDAO.addProduct(new Product("Regalia Driving Gloves | Brown L",
                                "Regalia Driving Gloves crafted with meticulous attention to detail.\nBrown L.", 297.66,
                        gloves, "luxury_driving_glove_2f5861c2-cd14-4713-a32e-26453347ee44", 1, "brown",
                        parentProduct1.getProductNummer()));
        productDAO.addProduct(new Product("Regalia Driving Gloves | Black S",
                                "Regalia Driving Gloves crafted with meticulous attention to detail.\nBlack S.", 297.66,
                        gloves, "luxury_driving_glove_2f5861c2-cd14-4713-a32e-26453347ee44", 1, "black",
                        parentProduct1.getProductNummer()));
        productDAO.addProduct(new Product("Regalia Driving Gloves | Black M",
                                "Regalia Driving Gloves crafted with meticulous attention to detail.\nBlack M.", 297.66,
                        gloves, "luxury_driving_glove_2f5861c2-cd14-4713-a32e-26453347ee44", 1, "black",
                        parentProduct1.getProductNummer()));
        productDAO.addProduct(new Product("Regalia Driving Gloves | Black L",
                                "Regalia Driving Gloves crafted with meticulous attention to detail.\nBlack L.", 297.66,
                        gloves, "luxury_driving_glove_2f5861c2-cd14-4713-a32e-26453347ee44", 1, "black",
                        parentProduct1.getProductNummer()));

        Product parentProduct2 = productDAO.addProduct(new Product("Cavendish Driving Gloves | Brown S",
                                "Cavendish Driving Gloves crafted with meticulous attention to detail.\nBrown S.",
                                71.69,
                        gloves, "luxury_driving_glove_ce5f0f1a-84a4-45b9-b5c3-37845c70f07a", 1, "brown"));
        productDAO.addProduct(new Product("Cavendish Driving Gloves | Brown M",
                                "Cavendish Driving Gloves crafted with meticulous attention to detail.\nBrown M.",
                                71.69,
                        gloves, "luxury_driving_glove_ce5f0f1a-84a4-45b9-b5c3-37845c70f07a", 1, "brown",
                        parentProduct2.getProductNummer()));
        productDAO.addProduct(new Product("Cavendish Driving Gloves | Brown L",
                                "Cavendish Driving Gloves crafted with meticulous attention to detail.\nBrown L.",
                                71.69,
                        gloves, "luxury_driving_glove_ce5f0f1a-84a4-45b9-b5c3-37845c70f07a", 1, "brown",
                        parentProduct2.getProductNummer()));
        productDAO.addProduct(new Product("Cavendish Driving Gloves | Black S",
                                "Cavendish Driving Gloves crafted with meticulous attention to detail.\nBlack S.",
                                71.69,
                        gloves, "luxury_driving_glove_ce5f0f1a-84a4-45b9-b5c3-37845c70f07a", 1, "black",
                        parentProduct2.getProductNummer()));
        productDAO.addProduct(new Product("Cavendish Driving Gloves | Black M",
                                "Cavendish Driving Gloves crafted with meticulous attention to detail.\nBlack M.",
                                71.69,
                        gloves, "luxury_driving_glove_ce5f0f1a-84a4-45b9-b5c3-37845c70f07a", 1, "black",
                        parentProduct2.getProductNummer()));
        productDAO.addProduct(new Product("Cavendish Driving Gloves | Black L",
                                "Cavendish Driving Gloves crafted with meticulous attention to detail.\nBlack L.",
                                71.69,
                        gloves, "luxury_driving_glove_ce5f0f1a-84a4-45b9-b5c3-37845c70f07a", 1, "black",
                        parentProduct2.getProductNummer()));

        productDAO.addProduct(new Product("Bellvista Vintage Champagne | Original",
                                "Bellvista Vintage Champagne crafted with meticulous attention to detail.", 265.79,
                                champagne,
                        "luxury_exclusive_cha_54705363-7b35-4527-86c9-a8f80f0c17ec", 1, "lightgray"));

        productDAO.addProduct(new Product("Montclair Vintage Champagne | Original",
                                "Montclair Vintage Champagne crafted with meticulous attention to detail.", 108.3,
                                champagne,
                        "luxury_exclusive_cha_b6f7f8be-51da-4f41-a9e8-9699ea3144cc", 1, "lightgray"));

        productDAO.addProduct(new Product("Aurelius Vintage Champagne | Original",
                                "Aurelius Vintage Champagne crafted with meticulous attention to detail.", 130.68,
                                champagne,
                        "luxury_exclusive_cha_ba9fa2e7-53ee-4456-ad7b-fd83ee4edd5f", 1, "lightgray"));

                Product parentProduct3 = productDAO.addProduct(new Product(
                                "Valencourt Leather Weekender Bag | Cognac Medium",
                                "Valencourt Leather Weekender Bag crafted with meticulous attention to detail.\nCognac Medium.",
                                281.61, bag, "luxury_exclusive_lad_62a8f984-df54-4bf3-87b4-01fc7b2c45d6", 1,
                                "cognac"));
        productDAO.addProduct(new Product("Valencourt Leather Weekender Bag | Cognac Large",
                                "Valencourt Leather Weekender Bag crafted with meticulous attention to detail.\nCognac Large.",
                                281.61, bag, "luxury_exclusive_lad_62a8f984-df54-4bf3-87b4-01fc7b2c45d6", 1,
                                "cognac",
                        parentProduct3.getProductNummer()));
        productDAO.addProduct(new Product("Valencourt Leather Weekender Bag | Ebony Medium",
                                "Valencourt Leather Weekender Bag crafted with meticulous attention to detail.\nEbony Medium.",
                                281.61, bag, "luxury_exclusive_lad_62a8f984-df54-4bf3-87b4-01fc7b2c45d6", 1,
                                "ebony",
                        parentProduct3.getProductNummer()));
        productDAO.addProduct(new Product("Valencourt Leather Weekender Bag | Ebony Large",
                                "Valencourt Leather Weekender Bag crafted with meticulous attention to detail.\nEbony Large.",
                                281.61, bag, "luxury_exclusive_lad_62a8f984-df54-4bf3-87b4-01fc7b2c45d6", 1,
                                "ebony",
                        parentProduct3.getProductNummer()));

                Product parentProduct4 = productDAO.addProduct(new Product(
                                "Elysian Leather Weekender Bag | Cognac Medium",
                                "Elysian Leather Weekender Bag crafted with meticulous attention to detail.\nCognac Medium.",
                                282.51, bag, "luxury_exclusive_lad_dabf92b7-3c71-48ff-ae18-d82ba51a06cc", 1,
                                "cognac"));
        productDAO.addProduct(new Product("Elysian Leather Weekender Bag | Cognac Large",
                                "Elysian Leather Weekender Bag crafted with meticulous attention to detail.\nCognac Large.",
                                282.51, bag, "luxury_exclusive_lad_dabf92b7-3c71-48ff-ae18-d82ba51a06cc", 1,
                                "cognac",
                        parentProduct4.getProductNummer()));
        productDAO.addProduct(new Product("Elysian Leather Weekender Bag | Ebony Medium",
                                "Elysian Leather Weekender Bag crafted with meticulous attention to detail.\nEbony Medium.",
                                282.51, bag, "luxury_exclusive_lad_dabf92b7-3c71-48ff-ae18-d82ba51a06cc", 1,
                                "ebony",
                        parentProduct4.getProductNummer()));
        productDAO.addProduct(new Product("Elysian Leather Weekender Bag | Ebony Large",
                                "Elysian Leather Weekender Bag crafted with meticulous attention to detail.\nEbony Large.",
                                282.51, bag, "luxury_exclusive_lad_dabf92b7-3c71-48ff-ae18-d82ba51a06cc", 1,
                                "ebony",
                        parentProduct4.getProductNummer()));

                Product parentProduct5 = productDAO.addProduct(new Product(
                                "Marquess Leather Weekender Bag | Cognac Medium",
                                "Marquess Leather Weekender Bag crafted with meticulous attention to detail.\nCognac Medium.",
                                239.62, bag, "luxury_exclusive_lad_e7331e71-ad22-4a09-99b4-6552021ff1ff", 1,
                                "cognac"));
        productDAO.addProduct(new Product("Marquess Leather Weekender Bag | Cognac Large",
                                "Marquess Leather Weekender Bag crafted with meticulous attention to detail.\nCognac Large.",
                                239.62, bag, "luxury_exclusive_lad_e7331e71-ad22-4a09-99b4-6552021ff1ff", 1,
                                "cognac",
                        parentProduct5.getProductNummer()));
        productDAO.addProduct(new Product("Marquess Leather Weekender Bag | Ebony Medium",
                                "Marquess Leather Weekender Bag crafted with meticulous attention to detail.\nEbony Medium.",
                                239.62, bag, "luxury_exclusive_lad_e7331e71-ad22-4a09-99b4-6552021ff1ff", 1,
                                "ebony",
                        parentProduct5.getProductNummer()));
        productDAO.addProduct(new Product("Marquess Leather Weekender Bag | Ebony Large",
                                "Marquess Leather Weekender Bag crafted with meticulous attention to detail.\nEbony Large.",
                                239.62, bag, "luxury_exclusive_lad_e7331e71-ad22-4a09-99b4-6552021ff1ff", 1,
                                "ebony",
                        parentProduct5.getProductNummer()));

                Product parentProduct6 = productDAO.addProduct(new Product(
                                "Altair Leather Weekender Bag | Cognac Medium",
                                "Altair Leather Weekender Bag crafted with meticulous attention to detail.\nCognac Medium.",
                                129.43, bag, "luxury_exclusive_lad_f31a18cd-cc5a-4d9d-9167-959db8ae6fd4", 1,
                                "cognac"));
        productDAO.addProduct(new Product("Altair Leather Weekender Bag | Cognac Large",
                                "Altair Leather Weekender Bag crafted with meticulous attention to detail.\nCognac Large.",
                                129.43, bag, "luxury_exclusive_lad_f31a18cd-cc5a-4d9d-9167-959db8ae6fd4", 1,
                                "cognac",
                        parentProduct6.getProductNummer()));
        productDAO.addProduct(new Product("Altair Leather Weekender Bag | Ebony Medium",
                                "Altair Leather Weekender Bag crafted with meticulous attention to detail.\nEbony Medium.",
                                129.43, bag, "luxury_exclusive_lad_f31a18cd-cc5a-4d9d-9167-959db8ae6fd4", 1,
                                "ebony",
                        parentProduct6.getProductNummer()));
        productDAO.addProduct(new Product("Altair Leather Weekender Bag | Ebony Large",
                                "Altair Leather Weekender Bag crafted with meticulous attention to detail.\nEbony Large.",
                                129.43, bag, "luxury_exclusive_lad_f31a18cd-cc5a-4d9d-9167-959db8ae6fd4", 1,
                                "ebony",
                        parentProduct6.getProductNummer()));

        productDAO.addProduct(new Product("Solstice Pomerol Grand Cru | Original",
                                "Solstice Pomerol Grand Cru crafted with meticulous attention to detail.", 76.35,
                                liquor,
                        "luxury_pomerol_vin_o_129b2912-6d61-4d62-b8ed-ae31f5d7c3e2", 1, "lightgray"));

        productDAO.addProduct(new Product("Regalia Pomerol Grand Cru | Original",
                                "Regalia Pomerol Grand Cru crafted with meticulous attention to detail.", 232.17,
                                liquor,
                        "luxury_pomerol_vin_o_1530ca0b-4386-4b7f-80dc-d17e897b2abd", 1, "lightgray"));

        productDAO.addProduct(new Product("Cavendish Pomerol Grand Cru | Original",
                                "Cavendish Pomerol Grand Cru crafted with meticulous attention to detail.", 279.68,
                                liquor,
                        "luxury_pomerol_vin_o_5daa00bb-399b-421b-9dc2-898838d1d864", 1, "lightgray"));

        productDAO.addProduct(new Product("Bellvista Diamond Tiara | Original",
                                "Bellvista Diamond Tiara crafted with meticulous attention to detail.", 5201.19,
                                tiara,
                        "luxury_tiara_on_a_so_65ec801c-e87d-4b77-8787-7815a105d6f1", 1, "lightgray"));

        Product parentProduct7 = productDAO.addProduct(new Product("Montclair Travel Umbrella | Black Compact",
                                "Montclair Travel Umbrella crafted with meticulous attention to detail.\nBlack Compact.",
                                191.24, umbrella, "luxury_umbrella_on_a_508e80d9-a01d-4e8a-8140-227355db45e9", 1,
                                "black"));
        productDAO.addProduct(new Product("Montclair Travel Umbrella | Black Full",
                                "Montclair Travel Umbrella crafted with meticulous attention to detail.\nBlack Full.",
                                191.24,
                        umbrella, "luxury_umbrella_on_a_508e80d9-a01d-4e8a-8140-227355db45e9", 1, "black",
                        parentProduct7.getProductNummer()));
        productDAO.addProduct(new Product("Montclair Travel Umbrella | Navy Compact",
                                "Montclair Travel Umbrella crafted with meticulous attention to detail.\nNavy Compact.",
                                191.24,
                                umbrella, "luxury_umbrella_on_a_508e80d9-a01d-4e8a-8140-227355db45e9", 1, "navy",
                        parentProduct7.getProductNummer()));
        productDAO.addProduct(new Product("Montclair Travel Umbrella | Navy Full",
                                "Montclair Travel Umbrella crafted with meticulous attention to detail.\nNavy Full.",
                                191.24,
                        umbrella, "luxury_umbrella_on_a_508e80d9-a01d-4e8a-8140-227355db45e9", 1, "navy",
                        parentProduct7.getProductNummer()));
        productDAO.addProduct(new Product("Montclair Travel Umbrella | Burgundy Compact",
                                "Montclair Travel Umbrella crafted with meticulous attention to detail.\nBurgundy Compact.",
                                191.24, umbrella, "luxury_umbrella_on_a_508e80d9-a01d-4e8a-8140-227355db45e9", 1,
                                "#7f001e",
                        parentProduct7.getProductNummer()));
        productDAO.addProduct(new Product("Montclair Travel Umbrella | Burgundy Full",
                                "Montclair Travel Umbrella crafted with meticulous attention to detail.\nBurgundy Full.",
                                191.24, umbrella, "luxury_umbrella_on_a_508e80d9-a01d-4e8a-8140-227355db45e9", 1,
                                "#7f001e",
                        parentProduct7.getProductNummer()));

        Product parentProduct8 = productDAO.addProduct(new Product("Aurelius Travel Umbrella | Black Compact",
                                "Aurelius Travel Umbrella crafted with meticulous attention to detail.\nBlack Compact.",
                                295.5,
                        umbrella, "luxury_umbrella_on_a_c4019831-204b-4cca-a2f0-c85c7c089c47", 1, "black"));
        productDAO.addProduct(new Product("Aurelius Travel Umbrella | Black Full",
                                "Aurelius Travel Umbrella crafted with meticulous attention to detail.\nBlack Full.",
                                295.5,
                        umbrella, "luxury_umbrella_on_a_c4019831-204b-4cca-a2f0-c85c7c089c47", 1, "black",
                        parentProduct8.getProductNummer()));
        productDAO.addProduct(new Product("Aurelius Travel Umbrella | Navy Compact",
                                "Aurelius Travel Umbrella crafted with meticulous attention to detail.\nNavy Compact.",
                                295.5,
                        umbrella, "luxury_umbrella_on_a_c4019831-204b-4cca-a2f0-c85c7c089c47", 1, "navy",
                        parentProduct8.getProductNummer()));
        productDAO.addProduct(new Product("Aurelius Travel Umbrella | Navy Full",
                                "Aurelius Travel Umbrella crafted with meticulous attention to detail.\nNavy Full.",
                                295.5,
                        umbrella, "luxury_umbrella_on_a_c4019831-204b-4cca-a2f0-c85c7c089c47", 1, "navy",
                        parentProduct8.getProductNummer()));
        productDAO.addProduct(new Product("Aurelius Travel Umbrella | Burgundy Compact",
                                "Aurelius Travel Umbrella crafted with meticulous attention to detail.\nBurgundy Compact.",
                                295.5, umbrella, "luxury_umbrella_on_a_c4019831-204b-4cca-a2f0-c85c7c089c47", 1,
                                "#7f001e",
                        parentProduct8.getProductNummer()));
        productDAO.addProduct(new Product("Aurelius Travel Umbrella | Burgundy Full",
                                "Aurelius Travel Umbrella crafted with meticulous attention to detail.\nBurgundy Full.",
                                295.5,
                        umbrella, "luxury_umbrella_on_a_c4019831-204b-4cca-a2f0-c85c7c089c47", 1, "#7f001e",
                        parentProduct8.getProductNummer()));

        Product parentProduct9 = productDAO.addProduct(new Product("Valencourt Travel Umbrella | Black Compact",
                                "Valencourt Travel Umbrella crafted with meticulous attention to detail.\nBlack Compact.",
                                72.35, umbrella, "luxury_umbrella_on_a_d3df2d70-abe2-4769-922f-7b2567ac863f", 1,
                                "black"));
        productDAO.addProduct(new Product("Valencourt Travel Umbrella | Black Full",
                                "Valencourt Travel Umbrella crafted with meticulous attention to detail.\nBlack Full.",
                                72.35,
                        umbrella, "luxury_umbrella_on_a_d3df2d70-abe2-4769-922f-7b2567ac863f", 1, "black",
                        parentProduct9.getProductNummer()));
        productDAO.addProduct(new Product("Valencourt Travel Umbrella | Navy Compact",
                                "Valencourt Travel Umbrella crafted with meticulous attention to detail.\nNavy Compact.",
                                72.35,
                                umbrella, "luxury_umbrella_on_a_d3df2d70-abe2-4769-922f-7b2567ac863f", 1, "navy",
                        parentProduct9.getProductNummer()));
        productDAO.addProduct(new Product("Valencourt Travel Umbrella | Navy Full",
                                "Valencourt Travel Umbrella crafted with meticulous attention to detail.\nNavy Full.",
                                72.35,
                        umbrella, "luxury_umbrella_on_a_d3df2d70-abe2-4769-922f-7b2567ac863f", 1, "navy",
                        parentProduct9.getProductNummer()));
        productDAO.addProduct(new Product("Valencourt Travel Umbrella | Burgundy Compact",
                                "Valencourt Travel Umbrella crafted with meticulous attention to detail.\nBurgundy Compact.",
                                72.35, umbrella, "luxury_umbrella_on_a_d3df2d70-abe2-4769-922f-7b2567ac863f", 1,
                                "#7f001e",
                        parentProduct9.getProductNummer()));
        productDAO.addProduct(new Product("Valencourt Travel Umbrella | Burgundy Full",
                                "Valencourt Travel Umbrella crafted with meticulous attention to detail.\nBurgundy Full.",
                                72.35, umbrella, "luxury_umbrella_on_a_d3df2d70-abe2-4769-922f-7b2567ac863f", 1,
                                "#7f001e",
                        parentProduct9.getProductNummer()));

        Product parentProduct10 = productDAO.addProduct(new Product("Elysian Travel Umbrella | Black Compact",
                                "Elysian Travel Umbrella crafted with meticulous attention to detail.\nBlack Compact.",
                                203.08,
                        umbrella, "luxury_umbrella_on_a_fb95f1ad-5c47-40f4-b4ef-36f6c99d8e6a", 1, "black"));
        productDAO.addProduct(new Product("Elysian Travel Umbrella | Black Full",
                                "Elysian Travel Umbrella crafted with meticulous attention to detail.\nBlack Full.",
                                203.08,
                        umbrella, "luxury_umbrella_on_a_fb95f1ad-5c47-40f4-b4ef-36f6c99d8e6a", 1, "black",
                        parentProduct10.getProductNummer()));
        productDAO.addProduct(new Product("Elysian Travel Umbrella | Navy Compact",
                                "Elysian Travel Umbrella crafted with meticulous attention to detail.\nNavy Compact.",
                                203.08,
                        umbrella, "luxury_umbrella_on_a_fb95f1ad-5c47-40f4-b4ef-36f6c99d8e6a", 1, "navy",
                        parentProduct10.getProductNummer()));
        productDAO.addProduct(new Product("Elysian Travel Umbrella | Navy Full",
                                "Elysian Travel Umbrella crafted with meticulous attention to detail.\nNavy Full.",
                                203.08,
                        umbrella, "luxury_umbrella_on_a_fb95f1ad-5c47-40f4-b4ef-36f6c99d8e6a", 1, "navy",
                        parentProduct10.getProductNummer()));
        productDAO.addProduct(new Product("Elysian Travel Umbrella | Burgundy Compact",
                                "Elysian Travel Umbrella crafted with meticulous attention to detail.\nBurgundy Compact.",
                                203.08, umbrella, "luxury_umbrella_on_a_fb95f1ad-5c47-40f4-b4ef-36f6c99d8e6a", 1,
                                "#7f001e",
                        parentProduct10.getProductNummer()));
        productDAO.addProduct(new Product("Elysian Travel Umbrella | Burgundy Full",
                                "Elysian Travel Umbrella crafted with meticulous attention to detail.\nBurgundy Full.",
                                203.08,
                        umbrella, "luxury_umbrella_on_a_fb95f1ad-5c47-40f4-b4ef-36f6c99d8e6a", 1, "#7f001e",
                        parentProduct10.getProductNummer()));

        productDAO.addProduct(new Product("Marquess Bordeaux Vintage | Original",
                                "Marquess Bordeaux Vintage crafted with meticulous attention to detail.", 113.27,
                                liquor,
                        "luxury_vin_on_a_soft_8548eac4-9ac5-495f-aba3-d965285cddeb", 1, "lightgray"));

        productDAO.addProduct(new Product("Altair Bordeaux Vintage | Original",
                                "Altair Bordeaux Vintage crafted with meticulous attention to detail.", 117.78,
                                liquor,
                        "luxury_vin_on_a_soft_da355fd1-e4ee-4413-82c7-55ad87fb318f", 1, "lightgray"));

                Product parentProduct11 = productDAO.addProduct(new Product(
                                "Solstice Mechanical Wristwatch | Black Leather",
                                "Solstice Mechanical Wristwatch crafted with meticulous attention to detail.\nBlack Leather.",
                                1201.31, jewelry, "luxury_watch_on_a_so_290b2d77-3b1d-47b1-b2fd-edb701ca1ca4", 1,
                                "black"));
        productDAO.addProduct(new Product("Solstice Mechanical Wristwatch | Navy Alligator",
                                "Solstice Mechanical Wristwatch crafted with meticulous attention to detail.\nNavy Alligator.",
                                1590.52, jewelry, "luxury_watch_on_a_so_290b2d77-3b1d-47b1-b2fd-edb701ca1ca4", 1,
                                "navy",
                        parentProduct11.getProductNummer()));
        productDAO.addProduct(new Product("Solstice Mechanical Wristwatch | Brown Leather",
                                "Solstice Mechanical Wristwatch crafted with meticulous attention to detail.\nBrown Leather.",
                                2620.74, jewelry, "luxury_watch_on_a_so_290b2d77-3b1d-47b1-b2fd-edb701ca1ca4", 1,
                                "brown",
                        parentProduct11.getProductNummer()));

                Product parentProduct12 = productDAO.addProduct(new Product(
                                "Regalia Mechanical Wristwatch | Brown Leather",
                                "Regalia Mechanical Wristwatch crafted with meticulous attention to detail.\nBrown Leather.",
                                2893.1, jewelry, "luxury_watch_on_a_so_bac9350b-e49c-4690-84e5-cfa7a00c9cfe", 1,
                                "brown"));
        productDAO.addProduct(new Product("Regalia Mechanical Wristwatch | Black Leather",
                                "Regalia Mechanical Wristwatch crafted with meticulous attention to detail.\nBlack Leather.",
                                2126.29, jewelry, "luxury_watch_on_a_so_bac9350b-e49c-4690-84e5-cfa7a00c9cfe", 1,
                                "black",
                        parentProduct12.getProductNummer()));

                Product parentProduct13 = productDAO.addProduct(new Product(
                                "Cavendish Mechanical Wristwatch | Black Leather",
                                "Cavendish Mechanical Wristwatch crafted with meticulous attention to detail.\nBlack Leather.",
                                1774.73, jewelry, "luxury_watch_on_a_so_d79c2cbd-f08f-4083-b97a-f58ca02706ee", 1,
                                "black"));
        productDAO.addProduct(new Product("Cavendish Mechanical Wristwatch | Navy Alligator",
                                "Cavendish Mechanical Wristwatch crafted with meticulous attention to detail.\nNavy Alligator.",
                                1226.35, jewelry, "luxury_watch_on_a_so_d79c2cbd-f08f-4083-b97a-f58ca02706ee", 1,
                                "navy",
                        parentProduct13.getProductNummer()));

        Product parentProduct14 = productDAO.addProduct(new Product(
                        "Glacier Chronograph 42 mm  - Glacier White | White Leather",
                                "Swiss-made chronograph with glacier-white dial and sapphire crystal.\nWhite Leather.",
                                4950.0,
                        jewelry, "22d0c1b1-3462-4b68-afb6-e3714f3ff310", 1, "white"));
        productDAO.addProduct(new Product("Glacier Chronograph 42 mm  - Glacier White | Navy Alligator",
                                "Swiss-made chronograph with glacier-white dial and sapphire crystal.\nNavy Alligator.",
                                5150.0,
                                jewelry, "22d0c1b1-3462-4b68-afb6-e3714f3ff310", 1, "navy",
                        parentProduct14.getProductNummer()));

        productDAO.addProduct(new Product("Glacier Chronograph 42 mm  - Midnight | Black Leather",
                                "Chronograph with midnight-blue dial, 48-h power reserve.\nBlack Leather.", 4950.0,
                                jewelry,
                        "38ea7915-4fe7-4bd8-9cfb-7d3a85143163", 1, "black"));

        Product parentProduct15 = productDAO.addProduct(new Product(
                        "Glacier Chronograph 42 mm  - Rose Gold | Beige Leather",
                                "Rose-gold PVD case with galvanic silver dial, automatic movement.\nBeige Leather.",
                                5150.0,
                        jewelry, "3002b4a7-ebfc-4f71-9a79-5c6e6e44695d", 1, "beige"));
        productDAO.addProduct(new Product("Glacier Chronograph 42 mm  - Rose Gold | Black Crocodile",
                                "Rose-gold PVD case with galvanic silver dial, automatic movement.\nBlack Crocodile.",
                                5250.0,
                        jewelry, "3002b4a7-ebfc-4f71-9a79-5c6e6e44695d", 1, "black",
                        parentProduct15.getProductNummer()));

                Product parentProduct16 = productDAO.addProduct(new Product(
                                "Papillon Jardin Silk Scarf 90 | Rose 90x90 cm",
                                "Whimsical butterfly-garden carré in pure mulberry silk.\nRose 90x90 cm.", 450.0,
                                scarf,
                        "product_image_of_a_colorful_h_061aafd0-5975-4ed7-8e2b-cff0f5d68dea", 1, "#e21e30"));
        productDAO.addProduct(new Product("Papillon Jardin Silk Scarf 90 | Rose 70x70 cm",
                                "Whimsical butterfly-garden carré in pure mulberry silk.\nRose 70x70 cm.", 350.0,
                                scarf,
                        "product_image_of_a_colorful_h_061aafd0-5975-4ed7-8e2b-cff0f5d68dea", 1, "#e21e30",
                        parentProduct16.getProductNummer()));
        productDAO.addProduct(new Product("Papillon Jardin Silk Scarf 90 | Indigo 90x90 cm",
                                "Whimsical butterfly-garden carré in pure mulberry silk.\nIndigo 90x90 cm.", 450.0,
                                scarf,
                        "product_image_of_a_colorful_h_061aafd0-5975-4ed7-8e2b-cff0f5d68dea", 1, "indigo",
                        parentProduct16.getProductNummer()));
        productDAO.addProduct(new Product("Papillon Jardin Silk Scarf 90 | Indigo 70x70 cm",
                                "Whimsical butterfly-garden carré in pure mulberry silk.\nIndigo 70x70 cm.", 350.0,
                                scarf,
                        "product_image_of_a_colorful_h_061aafd0-5975-4ed7-8e2b-cff0f5d68dea", 1, "indigo",
                        parentProduct16.getProductNummer()));
        productDAO.addProduct(new Product("Papillon Jardin Silk Scarf 90 | Gold 90x90 cm",
                                "Whimsical butterfly-garden carré in pure mulberry silk.\nGold 90x90 cm.", 450.0,
                                scarf,
                        "product_image_of_a_colorful_h_061aafd0-5975-4ed7-8e2b-cff0f5d68dea", 1, "gold",
                        parentProduct16.getProductNummer()));
        productDAO.addProduct(new Product("Papillon Jardin Silk Scarf 90 | Gold 70x70 cm",
                                "Whimsical butterfly-garden carré in pure mulberry silk.\nGold 70x70 cm.", 350.0,
                                scarf,
                        "product_image_of_a_colorful_h_061aafd0-5975-4ed7-8e2b-cff0f5d68dea", 1, "gold",
                        parentProduct16.getProductNummer()));

                Product parentProduct17 = productDAO.addProduct(new Product(
                                "Nautilus Plumage Silk Scarf 90 | Rose 90x90 cm",
                                "Deep-sea plumage pattern on silk twill with hand-rolled edges.\nRose 90x90 cm.", 450.0,
                        scarf, "product_image_of_a_hermes_sca_1ef1e5c4-8d9c-4a87-ad6b-47e33e08dc74", 1,
                        "#e21e30"));
        productDAO.addProduct(new Product("Nautilus Plumage Silk Scarf 90 | Rose 70x70 cm",
                                "Deep-sea plumage pattern on silk twill with hand-rolled edges.\nRose 70x70 cm.", 350.0,
                                scarf, "product_image_of_a_hermes_sca_1ef1e5c4-8d9c-4a87-ad6b-47e33e08dc74", 1,
                                "#e21e30",
                        parentProduct17.getProductNummer()));
        productDAO.addProduct(new Product("Nautilus Plumage Silk Scarf 90 | Indigo 90x90 cm",
                                "Deep-sea plumage pattern on silk twill with hand-rolled edges.\nIndigo 90x90 cm.",
                                450.0,
                                scarf, "product_image_of_a_hermes_sca_1ef1e5c4-8d9c-4a87-ad6b-47e33e08dc74", 1,
                                "indigo",
                        parentProduct17.getProductNummer()));
        productDAO.addProduct(new Product("Nautilus Plumage Silk Scarf 90 | Indigo 70x70 cm",
                                "Deep-sea plumage pattern on silk twill with hand-rolled edges.\nIndigo 70x70 cm.",
                                350.0,
                                scarf, "product_image_of_a_hermes_sca_1ef1e5c4-8d9c-4a87-ad6b-47e33e08dc74", 1,
                                "indigo",
                        parentProduct17.getProductNummer()));
        productDAO.addProduct(new Product("Nautilus Plumage Silk Scarf 90 | Gold 90x90 cm",
                                "Deep-sea plumage pattern on silk twill with hand-rolled edges.\nGold 90x90 cm.", 450.0,
                                scarf, "product_image_of_a_hermes_sca_1ef1e5c4-8d9c-4a87-ad6b-47e33e08dc74", 1,
                                "gold",
                        parentProduct17.getProductNummer()));
        productDAO.addProduct(new Product("Nautilus Plumage Silk Scarf 90 | Gold 70x70 cm",
                                "Deep-sea plumage pattern on silk twill with hand-rolled edges.\nGold 70x70 cm.", 350.0,
                                scarf, "product_image_of_a_hermes_sca_1ef1e5c4-8d9c-4a87-ad6b-47e33e08dc74", 1,
                                "gold",
                        parentProduct17.getProductNummer()));

                Product parentProduct18 = productDAO.addProduct(new Product(
                                "Floraison Floral Silk Scarf 90 | Rose 90x90 cm",
                                "Vibrant floral motif inspired by spring gardens, on silk twill.\nRose 90x90 cm.",
                                450.0,
                        scarf, "product_image_of_a_hermes_sca_195b0e7b-233b-4514-b70a-f82a7f47b9bf", 1,
                        "#e21e30"));
        productDAO.addProduct(new Product("Floraison Floral Silk Scarf 90 | Rose 70x70 cm",
                                "Vibrant floral motif inspired by spring gardens, on silk twill.\nRose 70x70 cm.",
                                350.0,
                                scarf, "product_image_of_a_hermes_sca_195b0e7b-233b-4514-b70a-f82a7f47b9bf", 1,
                                "#e21e30",
                        parentProduct18.getProductNummer()));
        productDAO.addProduct(new Product("Floraison Floral Silk Scarf 90 | Indigo 90x90 cm",
                                "Vibrant floral motif inspired by spring gardens, on silk twill.\nIndigo 90x90 cm.",
                                450.0,
                                scarf, "product_image_of_a_hermes_sca_195b0e7b-233b-4514-b70a-f82a7f47b9bf", 1,
                                "indigo",
                        parentProduct18.getProductNummer()));
        productDAO.addProduct(new Product("Floraison Floral Silk Scarf 90 | Indigo 70x70 cm",
                                "Vibrant floral motif inspired by spring gardens, on silk twill.\nIndigo 70x70 cm.",
                                350.0,
                                scarf, "product_image_of_a_hermes_sca_195b0e7b-233b-4514-b70a-f82a7f47b9bf", 1,
                                "indigo",
                        parentProduct18.getProductNummer()));
        productDAO.addProduct(new Product("Floraison Floral Silk Scarf 90 | Gold 90x90 cm",
                                "Vibrant floral motif inspired by spring gardens, on silk twill.\nGold 90x90 cm.",
                                450.0,
                                scarf, "product_image_of_a_hermes_sca_195b0e7b-233b-4514-b70a-f82a7f47b9bf", 1,
                                "gold",
                        parentProduct18.getProductNummer()));
        productDAO.addProduct(new Product("Floraison Floral Silk Scarf 90 | Gold 70x70 cm",
                                "Vibrant floral motif inspired by spring gardens, on silk twill.\nGold 70x70 cm.",
                                350.0,
                                scarf, "product_image_of_a_hermes_sca_195b0e7b-233b-4514-b70a-f82a7f47b9bf", 1,
                                "gold",
                        parentProduct18.getProductNummer()));

                Product parentProduct19 = productDAO.addProduct(new Product(
                                "Metropolis Minimal Watch 40 mm | Black Leather",
                                "Ultra-thin quartz dress watch with onyx dial and steel case.\nBlack Leather.", 695.0,
                                jewelry, "product_image_of_an_watch_sui_afa101bd-9203-4329-8fe6-9f9662701f3c", 1,
                                "black"));
        productDAO.addProduct(new Product("Metropolis Minimal Watch 40 mm | Tan Leather",
                                "Ultra-thin quartz dress watch with onyx dial and steel case.\nTan Leather.", 695.0,
                                jewelry,
                                "product_image_of_an_watch_sui_afa101bd-9203-4329-8fe6-9f9662701f3c", 1, "tan",
                        parentProduct19.getProductNummer()));

        productDAO.addProduct(new Product("Hand-Stitched Cardholder  - Cognac | Cognac",
                                "Slim Barenia calfskin cardholder saddle-stitched by hand.\nCognac.", 195.0, cardholder,
                        "luxury_hand-stitched_1bc1ef8b-a097-46e7-b911-16d3ca44b51e", 1, "cognac"));

        productDAO.addProduct(new Product("Hand-Stitched Cardholder  - Ebony | Ebony",
                                "Saddle-stitched full-grain leather cardholder in rich ebony.\nEbony.", 195.0,
                                cardholder,
                        "luxury_hand-stitched_2da14f27-20e5-4447-99f6-89544a6babe3", 1, "ebony"));

        productDAO.addProduct(new Product("Hand-Stitched Cardholder  - Emerald | Emerald",
                                "Emerald-green calfskin cardholder with tonal stitching.\nEmerald.", 195.0, cardholder,
                        "luxury_hand-stitched_ba7bfe0f-cd1a-463e-824e-f8980a0ff2fa", 1, "emerald"));

        productDAO.addProduct(new Product("Solstice Classic Watch 38 mm  - Brown | Brown Leather",
                                "Two-hand automatic with espresso dial and brown strap.\nBrown Leather.", 1295.0,
                                jewelry,
                        "luxury_watch_on_a_so_0f2685db-758c-4129-a967-f661c33a42c3", 1, "brown"));

        Product parentProduct20 = productDAO.addProduct(new Product(
                        "Solstice Classic Watch 38 mm  - Black | Black Leather",
                                "Automatic watch with sunburst dial and black strap.\nBlack Leather.", 1295.0,
                                jewelry,
                        "luxury_watch_on_a_so_4d0a31fd-0aee-44fc-9a78-9798cc032fa0", 1, "black"));
        productDAO.addProduct(new Product("Solstice Classic Watch 38 mm  - Black | Steel Mesh",
                                "Automatic watch with sunburst dial and black strap.\nSteel Mesh.", 1345.0, jewelry,
                        "luxury_watch_on_a_so_4d0a31fd-0aee-44fc-9a78-9798cc032fa0", 1, "steel",
                        parentProduct20.getProductNummer()));

        productDAO.addProduct(new Product("Solstice Classic Watch 38 mm  - Burgundy | Burgundy Leather",
                                "Rose-gold PVD case paired with burgundy leather strap.\nBurgundy Leather.", 1295.0,
                                jewelry,
                                "luxury_watch_on_a_so_229a6aa9-8857-4dd7-a623-43f1e1d0f563", 1, "#7f001e"));

                Product parentProduct21 = productDAO.addProduct(new Product(
                                "Horizon Dual-Time Watch 41 mm | Black Leather",
                                "Automatic dual-time watch with sapphire case-back.\nBlack Leather.", 1595.0,
                                jewelry,
                        "luxury_watch_on_a_so_731901df-f63e-400a-aba2-fe7186d0d3c5", 1, "black"));
        productDAO.addProduct(new Product("Horizon Dual-Time Watch 41 mm | Navy Leather",
                                "Automatic dual-time watch with sapphire case-back.\nNavy Leather.", 1595.0, jewelry,
                        "luxury_watch_on_a_so_731901df-f63e-400a-aba2-fe7186d0d3c5", 1, "navy",
                        parentProduct21.getProductNummer()));

        productDAO.addProduct(new Product("Meridian Skeleton Watch 44 mm | Black Crocodile",
                                "Open-worked movement showcased under dome sapphire crystal.\nBlack Crocodile.", 2450.0,
                        jewelry, "luxury_watch_on_a_so_b983de4d-1367-4cf5-be41-b26dc2c48021", 1, "black"));

        Product parentProduct22 = productDAO.addProduct(new Product("Apex Diver 43 mm | Black",
                                "Professional diver’s watch water-resistant to 30 ATM.\nBlack.", 975.0, jewelry,
                        "luxury_watch_on_a_so_c87cef09-de81-4647-83f8-2c0ce9a8cecc", 1, "black"));
        productDAO.addProduct(new Product("Apex Diver 43 mm | Blue",
                                "Professional diver’s watch water-resistant to 30 ATM.\nBlue.", 975.0, jewelry,
                        "luxury_watch_on_a_so_c87cef09-de81-4647-83f8-2c0ce9a8cecc", 1, "blue",
                        parentProduct22.getProductNummer()));
        productDAO.addProduct(new Product("Apex Diver 43 mm | Green",
                                "Professional diver’s watch water-resistant to 30 ATM.\nGreen.", 975.0, jewelry,
                        "luxury_watch_on_a_so_c87cef09-de81-4647-83f8-2c0ce9a8cecc", 1, "green",
                        parentProduct22.getProductNummer()));

        Product parentProduct23 = productDAO.addProduct(new Product("Altitude Pilot Watch 42 mm | Tan Leather",
                                "Pilot chronograph with oversized crown and luminous numerals.\nTan Leather.", 1125.0,
                        jewelry, "luxury_watch_on_a_so_d7f52fe2-b780-4990-8511-e49acb335821", 1, "tan"));
        productDAO.addProduct(new Product("Altitude Pilot Watch 42 mm | Black Leather",
                                "Pilot chronograph with oversized crown and luminous numerals.\nBlack Leather.", 1125.0,
                        jewelry, "luxury_watch_on_a_so_d7f52fe2-b780-4990-8511-e49acb335821", 1, "black",
                        parentProduct23.getProductNummer()));

        productDAO.addProduct(new Product("Face Cream A968 | 50 ml",
                                "Rich hydrating  cream with botanical extracts, suitable for all skin types.\n50 ml.",
                                79.0,
                                beauty, "luxury_aesop_creme_o_8a6b4c5f-7a9d-4771-bef3-32c6885ae870", 1,
                                "lightgray"));

        Product parentProduct24 = productDAO.addProduct(new Product("Face Cream FF88 | 100 ml",
                                "Rich hydrating  cream with botanical extracts, suitable for all skin types.\n100 ml.",
                                129.0,
                                beauty, "luxury_aesop_creme_o_8f05e5d5-b318-4c48-85f3-0e24c4495648", 1,
                                "lightgray"));
        productDAO.addProduct(new Product("Face Cream FF88 | 50 ml",
                                "Rich hydrating  cream with botanical extracts, suitable for all skin types.\n50 ml.",
                                79.0,
                        beauty, "luxury_aesop_creme_o_8f05e5d5-b318-4c48-85f3-0e24c4495648", 1, "lightgray",
                        parentProduct24.getProductNummer()));

        Product parentProduct25 = productDAO.addProduct(new Product("Bracelet 6903 | 17 cm",
                                "Hand-crafted 18-karat gold bracelet with polished finish.\n17 cm.", 1474.19,
                                jewelry,
                        "luxury_bracelet_on_a_466c5e9c-ffb1-4f71-9f0a-c5f6c47bc182", 1, "lightgray"));
        productDAO.addProduct(new Product("Bracelet 6903 | 18 cm",
                                "Hand-crafted 18-karat gold bracelet with polished finish.\n18 cm.", 2533.34,
                                jewelry,
                        "luxury_bracelet_on_a_466c5e9c-ffb1-4f71-9f0a-c5f6c47bc182", 1, "lightgray",
                        parentProduct25.getProductNummer()));
        productDAO.addProduct(new Product("Bracelet 6903 | 15 cm",
                                "Hand-crafted 18-karat gold bracelet with polished finish.\n15 cm.", 1948.37,
                                jewelry,
                        "luxury_bracelet_on_a_466c5e9c-ffb1-4f71-9f0a-c5f6c47bc182", 1, "lightgray",
                        parentProduct25.getProductNummer()));
        productDAO.addProduct(new Product("Bracelet 6903 | 16 cm",
                                "Hand-crafted 18-karat gold bracelet with polished finish.\n16 cm.", 2844.95,
                                jewelry,
                        "luxury_bracelet_on_a_466c5e9c-ffb1-4f71-9f0a-c5f6c47bc182", 1, "lightgray",
                        parentProduct25.getProductNummer()));

        Product parentProduct26 = productDAO.addProduct(new Product("Bracelet 9574 | 15 cm",
                                "Hand-crafted 18-karat gold bracelet with polished finish.\n15 cm.", 2678.53,
                                jewelry,
                        "luxury_bracelet_on_a_59866f3a-2f3c-4c62-8719-7c5afd2103ce", 1, "lightgray"));
        productDAO.addProduct(new Product("Bracelet 9574 | 18 cm",
                                "Hand-crafted 18-karat gold bracelet with polished finish.\n18 cm.", 2743.97,
                                jewelry,
                        "luxury_bracelet_on_a_59866f3a-2f3c-4c62-8719-7c5afd2103ce", 1, "lightgray",
                        parentProduct26.getProductNummer()));

        Product parentProduct27 = productDAO.addProduct(new Product("Bracelet EA94 | 18 cm",
                                "Hand-crafted 18-karat gold bracelet with polished finish.\n18 cm.", 2849.08,
                                jewelry,
                        "luxury_bracelet_on_a_acbacc30-4dc7-4c05-8b30-83b6f8380a44", 1, "lightgray"));
        productDAO.addProduct(new Product("Bracelet EA94 | 17 cm",
                                "Hand-crafted 18-karat gold bracelet with polished finish.\n17 cm.", 1949.42,
                                jewelry,
                        "luxury_bracelet_on_a_acbacc30-4dc7-4c05-8b30-83b6f8380a44", 1, "lightgray",
                        parentProduct27.getProductNummer()));
        productDAO.addProduct(new Product("Bracelet EA94 | 16 cm",
                                "Hand-crafted 18-karat gold bracelet with polished finish.\n16 cm.", 2086.08,
                                jewelry,
                        "luxury_bracelet_on_a_acbacc30-4dc7-4c05-8b30-83b6f8380a44", 1, "lightgray",
                        parentProduct27.getProductNummer()));

        Product parentProduct28 = productDAO.addProduct(new Product("Model Car F710 | Verde British",
                                "1:18 scale die-cast model of an iconic sports car.\nVerde British.", 203.68,
                                decoration,
                        "luxury_car_on_a_soft_3c46a2e2-2f85-416e-ad13-bafb62bb110f", 1, "verde british"));
        productDAO.addProduct(new Product("Model Car F710 | Rosso Corsa",
                                "1:18 scale die-cast model of an iconic sports car.\nRosso Corsa.", 196.94, decoration,
                        "luxury_car_on_a_soft_3c46a2e2-2f85-416e-ad13-bafb62bb110f", 1, "rosso corsa",
                        parentProduct28.getProductNummer()));

        Product parentProduct29 = productDAO.addProduct(new Product("Model Car 0FD5 | Giallo Modena",
                                "1:18 scale die-cast model of an iconic sports car.\nGiallo Modena.", 331.58,
                                decoration,
                        "luxury_car_on_a_soft_a719d3c2-5cf3-4665-88cf-57f199820740", 1, "giallo modena"));
        productDAO.addProduct(new Product("Model Car 0FD5 | Rosso Corsa",
                                "1:18 scale die-cast model of an iconic sports car.\nRosso Corsa.", 203.3, decoration,
                        "luxury_car_on_a_soft_a719d3c2-5cf3-4665-88cf-57f199820740", 1, "rosso corsa",
                        parentProduct29.getProductNummer()));
        productDAO.addProduct(new Product("Model Car 0FD5 | Verde British",
                                "1:18 scale die-cast model of an iconic sports car.\nVerde British.", 372.19,
                                decoration,
                        "luxury_car_on_a_soft_a719d3c2-5cf3-4665-88cf-57f199820740", 1, "verde british",
                        parentProduct29.getProductNummer()));

        Product parentProduct30 = productDAO.addProduct(new Product("Cufflinks EB50 | 18k Rose Gold",
                                "Polished sterling-silver cufflinks with T-bar fastening.\n18k Rose Gold.", 441.9,
                                jewelry,
                        "luxury_cufflinks_on__96bd943c-74ab-47db-84b0-863c30191cda", 1, "18k rose gold"));
        productDAO.addProduct(new Product("Cufflinks EB50 | White Gold",
                                "Polished sterling-silver cufflinks with T-bar fastening.\nWhite Gold.", 390.64,
                                jewelry,
                        "luxury_cufflinks_on__96bd943c-74ab-47db-84b0-863c30191cda", 1, "white gold",
                        parentProduct30.getProductNummer()));
        productDAO.addProduct(new Product("Cufflinks EB50 | Sterling Silver",
                                "Polished sterling-silver cufflinks with T-bar fastening.\nSterling Silver.", 443.94,
                                jewelry, "luxury_cufflinks_on__96bd943c-74ab-47db-84b0-863c30191cda", 1,
                                "sterling silver",
                        parentProduct30.getProductNummer()));

        productDAO.addProduct(new Product("Cufflinks C953 | White Gold",
                                "Polished sterling-silver cufflinks with T-bar fastening.\nWhite Gold.", 297.11,
                                jewelry,
                        "luxury_cufflinks_on__b6055b3f-cb87-4782-a17c-82772efe35c4", 1, "white gold"));

        Product parentProduct31 = productDAO.addProduct(new Product("Cufflinks 470B | Sterling Silver",
                                "Polished sterling-silver cufflinks with T-bar fastening.\nSterling Silver.", 357.73,
                                jewelry, "luxury_cufflinks_on__f63b0855-6455-4d76-bb8c-2468b3b44f19", 1,
                                "sterling silver"));
        productDAO.addProduct(new Product("Cufflinks 470B | 18k Rose Gold",
                                "Polished sterling-silver cufflinks with T-bar fastening.\n18k Rose Gold.", 567.51,
                                jewelry,
                        "luxury_cufflinks_on__f63b0855-6455-4d76-bb8c-2468b3b44f19", 1, "18k rose gold",
                        parentProduct31.getProductNummer()));

        Product parentProduct32 = productDAO.addProduct(new Product("Eau de Parfum F6B2 | 100 ml",
                                "Artisanal niche fragrance blended with natural essences.\n100 ml.", 185.0, beauty,
                        "luxury_ds_durga_on_a_71bd5ed3-8efb-4daf-823e-8707c32b4016", 1, "lightgray"));
        productDAO.addProduct(new Product("Eau de Parfum F6B2 | 50 ml",
                                "Artisanal niche fragrance blended with natural essences.\n50 ml.", 125.0, beauty,
                        "luxury_ds_durga_on_a_71bd5ed3-8efb-4daf-823e-8707c32b4016", 1, "lightgray",
                        parentProduct32.getProductNummer()));

        productDAO.addProduct(new Product("Eau de Parfum CB4C | 50 ml",
                                "Artisanal niche fragrance blended with natural essences.\n50 ml.", 125.0, beauty,
                        "luxury_ds_durga_on_a_88a75a7d-b894-437d-ad3f-de717a37bdc1", 1, "lightgray"));

        Product parentProduct33 = productDAO.addProduct(new Product("Silk Scarf 4CE8 | Cerulean Blue 90x90 cm",
                                "100 % mulberry silk scarf with hand-rolled edges.\nCerulean Blue 90x90 cm.", 450.0,
                                scarf,
                                "luxury_hermes_scarf__7ec55aff-307c-447b-a6e6-bc46e2c810cb", 1, "cerulean blue"));
        productDAO.addProduct(new Product("Silk Scarf 4CE8 | Emerald 90x90 cm",
                                "100 % mulberry silk scarf with hand-rolled edges.\nEmerald 90x90 cm.", 450.0,
                                scarf,
                        "luxury_hermes_scarf__7ec55aff-307c-447b-a6e6-bc46e2c810cb", 1, "emerald",
                        parentProduct33.getProductNummer()));
        productDAO.addProduct(new Product("Silk Scarf 4CE8 | Crimson 90x90 cm",
                                "100 % mulberry silk scarf with hand-rolled edges.\nCrimson 90x90 cm.", 450.0,
                                scarf,
                        "luxury_hermes_scarf__7ec55aff-307c-447b-a6e6-bc46e2c810cb", 1, "crimson",
                        parentProduct33.getProductNummer()));
        productDAO.addProduct(new Product("Silk Scarf 4CE8 | Sunset 90x90 cm",
                                "100 % mulberry silk scarf with hand-rolled edges.\nSunset 90x90 cm.", 450.0,
                                scarf,
                        "luxury_hermes_scarf__7ec55aff-307c-447b-a6e6-bc46e2c810cb", 1, "sunset",
                        parentProduct33.getProductNummer()));

        productDAO.addProduct(new Product("Silk Scarf 31EF | Cerulean Blue 90x90 cm",
                                "100 % mulberry silk scarf with hand-rolled edges.\nCerulean Blue 90x90 cm.", 450.0,
                                scarf,
                                "luxury_hermes_scarf__12a4d09c-ee91-4af6-8ce8-566c6acb36f6", 1, "cerulean blue"));

        Product parentProduct34 = productDAO.addProduct(new Product("Leather Loafers 5041 | Brown 41",
                                "Hand-stitched calfskin loafers with leather sole.\nBrown 41.", 465.84, shoes,
                        "luxury_loafers_on_a__5418f261-12a2-47fe-8c7a-caefec375668", 1, "brown"));
        productDAO.addProduct(new Product("Leather Loafers 5041 | Brown 42",
                                "Hand-stitched calfskin loafers with leather sole.\nBrown 42.", 465.84, shoes   ,
                        "luxury_loafers_on_a__5418f261-12a2-47fe-8c7a-caefec375668", 1, "brown",
                        parentProduct34.getProductNummer()));
        productDAO.addProduct(new Product("Leather Loafers 5041 | Brown 43",
                                "Hand-stitched calfskin loafers with leather sole.\nBrown 43.", 465.84, shoes,
                        "luxury_loafers_on_a__5418f261-12a2-47fe-8c7a-caefec375668", 1, "brown",
                        parentProduct34.getProductNummer()));
        productDAO.addProduct(new Product("Leather Loafers 5041 | Brown 44",
                                "Hand-stitched calfskin loafers with leather sole.\nBrown 44.", 465.84, shoes,
                        "luxury_loafers_on_a__5418f261-12a2-47fe-8c7a-caefec375668", 1, "brown",
                        parentProduct34.getProductNummer()));
        productDAO.addProduct(new Product("Leather Loafers 5041 | Brown 45",
                                "Hand-stitched calfskin loafers with leather sole.\nBrown 45.", 465.84, shoes,
                        "luxury_loafers_on_a__5418f261-12a2-47fe-8c7a-caefec375668", 1, "brown",
                        parentProduct34.getProductNummer()));
        productDAO.addProduct(new Product("Leather Loafers 5041 | Black 41",
                                "Hand-stitched calfskin loafers with leather sole.\nBlack 41.", 465.84, shoes,
                        "luxury_loafers_on_a__5418f261-12a2-47fe-8c7a-caefec375668", 1, "black",
                        parentProduct34.getProductNummer()));
        productDAO.addProduct(new Product("Leather Loafers 5041 | Black 42",
                                "Hand-stitched calfskin loafers with leather sole.\nBlack 42.", 465.84, shoes,
                        "luxury_loafers_on_a__5418f261-12a2-47fe-8c7a-caefec375668", 1, "black",
                        parentProduct34.getProductNummer()));
        productDAO.addProduct(new Product("Leather Loafers 5041 | Black 43",
                                "Hand-stitched calfskin loafers with leather sole.\nBlack 43.", 465.84, shoes,
                        "luxury_loafers_on_a__5418f261-12a2-47fe-8c7a-caefec375668", 1, "black",
                        parentProduct34.getProductNummer()));
        productDAO.addProduct(new Product("Leather Loafers 5041 | Black 44",
                                "Hand-stitched calfskin loafers with leather sole.\nBlack 44.", 465.84, shoes,
                        "luxury_loafers_on_a__5418f261-12a2-47fe-8c7a-caefec375668", 1, "black",
                        parentProduct34.getProductNummer()));
        productDAO.addProduct(new Product("Leather Loafers 5041 | Black 45",
                                "Hand-stitched calfskin loafers with leather sole.\nBlack 45.", 465.84, shoes,
                        "luxury_loafers_on_a__5418f261-12a2-47fe-8c7a-caefec375668", 1, "black",
                        parentProduct34.getProductNummer()));

        Product parentProduct35 = productDAO.addProduct(new Product("Leather Loafers 6B58 | Brown 41",
                                "Hand-stitched calfskin loafers with leather sole.\nBrown 41.", 523.97, shoes,
                        "luxury_loafers_on_a__bd1bd967-ad6b-430f-a644-3cce20e11e45", 1, "brown"));
        productDAO.addProduct(new Product("Leather Loafers 6B58 | Brown 42",
                                "Hand-stitched calfskin loafers with leather sole.\nBrown 42.", 523.97, shoes,
                        "luxury_loafers_on_a__bd1bd967-ad6b-430f-a644-3cce20e11e45", 1, "brown",
                        parentProduct35.getProductNummer()));
        productDAO.addProduct(new Product("Leather Loafers 6B58 | Brown 43",
                                "Hand-stitched calfskin loafers with leather sole.\nBrown 43.", 523.97, shoes,
                        "luxury_loafers_on_a__bd1bd967-ad6b-430f-a644-3cce20e11e45", 1, "brown",
                        parentProduct35.getProductNummer()));
        productDAO.addProduct(new Product("Leather Loafers 6B58 | Brown 44",
                                "Hand-stitched calfskin loafers with leather sole.\nBrown 44.", 523.97, shoes,
                        "luxury_loafers_on_a__bd1bd967-ad6b-430f-a644-3cce20e11e45", 1, "brown",
                        parentProduct35.getProductNummer()));
        productDAO.addProduct(new Product("Leather Loafers 6B58 | Brown 45",
                                "Hand-stitched calfskin loafers with leather sole.\nBrown 45.", 523.97, shoes,
                        "luxury_loafers_on_a__bd1bd967-ad6b-430f-a644-3cce20e11e45", 1, "brown",
                        parentProduct35.getProductNummer()));
        productDAO.addProduct(new Product("Leather Loafers 6B58 | Black 41",
                                "Hand-stitched calfskin loafers with leather sole.\nBlack 41.", 523.97, shoes,
                        "luxury_loafers_on_a__bd1bd967-ad6b-430f-a644-3cce20e11e45", 1, "black",
                        parentProduct35.getProductNummer()));
        productDAO.addProduct(new Product("Leather Loafers 6B58 | Black 42",
                                "Hand-stitched calfskin loafers with leather sole.\nBlack 42.", 523.97, shoes,
                        "luxury_loafers_on_a__bd1bd967-ad6b-430f-a644-3cce20e11e45", 1, "black",
                        parentProduct35.getProductNummer()));
        productDAO.addProduct(new Product("Leather Loafers 6B58 | Black 43",
                                "Hand-stitched calfskin loafers with leather sole.\nBlack 43.", 523.97, shoes,
                        "luxury_loafers_on_a__bd1bd967-ad6b-430f-a644-3cce20e11e45", 1, "black",
                        parentProduct35.getProductNummer()));
        productDAO.addProduct(new Product("Leather Loafers 6B58 | Black 44",
                                "Hand-stitched calfskin loafers with leather sole.\nBlack 44.", 523.97, shoes,
                        "luxury_loafers_on_a__bd1bd967-ad6b-430f-a644-3cce20e11e45", 1, "black",
                        parentProduct35.getProductNummer()));
        productDAO.addProduct(new Product("Leather Loafers 6B58 | Black 45",
                                "Hand-stitched calfskin loafers with leather sole.\nBlack 45.", 523.97, shoes,
                        "luxury_loafers_on_a__bd1bd967-ad6b-430f-a644-3cce20e11e45", 1, "black",
                        parentProduct35.getProductNummer()));

        Product parentProduct36 = productDAO.addProduct(new Product("Leather Loafers B21F | Brown 41",
                                "Hand-stitched calfskin loafers with leather sole.\nBrown 41.", 561.26, shoes,
                        "luxury_loafers_on_a__cf8ed129-0f74-4421-9e26-05dd569e2672", 1, "brown"));
        productDAO.addProduct(new Product("Leather Loafers B21F | Brown 42",
                                "Hand-stitched calfskin loafers with leather sole.\nBrown 42.", 561.26, shoes,
                        "luxury_loafers_on_a__cf8ed129-0f74-4421-9e26-05dd569e2672", 1, "brown",
                        parentProduct36.getProductNummer()));
        productDAO.addProduct(new Product("Leather Loafers B21F | Brown 43",
                                "Hand-stitched calfskin loafers with leather sole.\nBrown 43.", 561.26, shoes,
                        "luxury_loafers_on_a__cf8ed129-0f74-4421-9e26-05dd569e2672", 1, "brown",
                        parentProduct36.getProductNummer()));
        productDAO.addProduct(new Product("Leather Loafers B21F | Brown 44",
                                "Hand-stitched calfskin loafers with leather sole.\nBrown 44.", 561.26, shoes,
                        "luxury_loafers_on_a__cf8ed129-0f74-4421-9e26-05dd569e2672", 1, "brown",
                        parentProduct36.getProductNummer()));
        productDAO.addProduct(new Product("Leather Loafers B21F | Brown 45",
                                "Hand-stitched calfskin loafers with leather sole.\nBrown 45.", 561.26, shoes,
                        "luxury_loafers_on_a__cf8ed129-0f74-4421-9e26-05dd569e2672", 1, "brown",
                        parentProduct36.getProductNummer()));
        productDAO.addProduct(new Product("Leather Loafers B21F | Black 41",
                                "Hand-stitched calfskin loafers with leather sole.\nBlack 41.", 561.26, shoes,
                        "luxury_loafers_on_a__cf8ed129-0f74-4421-9e26-05dd569e2672", 1, "black",
                        parentProduct36.getProductNummer()));
        productDAO.addProduct(new Product("Leather Loafers B21F | Black 42",
                                "Hand-stitched calfskin loafers with leather sole.\nBlack 42.", 561.26, shoes,
                        "luxury_loafers_on_a__cf8ed129-0f74-4421-9e26-05dd569e2672", 1, "black",
                        parentProduct36.getProductNummer()));
        productDAO.addProduct(new Product("Leather Loafers B21F | Black 43",
                                "Hand-stitched calfskin loafers with leather sole.\nBlack 43.", 561.26, shoes,
                        "luxury_loafers_on_a__cf8ed129-0f74-4421-9e26-05dd569e2672", 1, "black",
                        parentProduct36.getProductNummer()));
        productDAO.addProduct(new Product("Leather Loafers B21F | Black 44",
                                "Hand-stitched calfskin loafers with leather sole.\nBlack 44.", 561.26, shoes,
                        "luxury_loafers_on_a__cf8ed129-0f74-4421-9e26-05dd569e2672", 1, "black",
                        parentProduct36.getProductNummer()));
        productDAO.addProduct(new Product("Leather Loafers B21F | Black 45",
                                "Hand-stitched calfskin loafers with leather sole.\nBlack 45.", 561.26, shoes,
                        "luxury_loafers_on_a__cf8ed129-0f74-4421-9e26-05dd569e2672", 1, "black",
                        parentProduct36.getProductNummer()));

        Product parentProduct37 = productDAO.addProduct(new Product("Necklace 9830 | 17 cm",
                                "Delicate 18-karat gold necklace with solitaire diamond pendant.\n17 cm.", 2890.77,
                                jewelry,
                        "luxury_necklace_on_a_61e2be6b-c8cc-478c-8aa4-197b267948fd", 1, "lightgray"));
        productDAO.addProduct(new Product("Necklace 9830 | 15 cm",
                                "Delicate 18-karat gold necklace with solitaire diamond pendant.\n15 cm.", 2469.13,
                                jewelry,
                        "luxury_necklace_on_a_61e2be6b-c8cc-478c-8aa4-197b267948fd", 1, "lightgray",
                        parentProduct37.getProductNummer()));

        Product parentProduct38 = productDAO.addProduct(new Product("Ring 21F0 | 54",
                                "18-karat gold ring with hand-set brilliant-cut diamonds.\n54.", 1515.29, jewelry,
                        "luxury_ring_on_a_sof_5157a06f-d2e3-4efc-ac3d-2a1ca8555750", 1, "lightgray"));
        productDAO.addProduct(new Product("Ring 21F0 | 52",
                                "18-karat gold ring with hand-set brilliant-cut diamonds.\n52.", 1211.34, jewelry,
                        "luxury_ring_on_a_sof_5157a06f-d2e3-4efc-ac3d-2a1ca8555750", 1, "lightgray",
                        parentProduct38.getProductNummer()));
                productDAO.addProduct(
                                new Product("Ring 21F0 | 58",
                                                "18-karat gold ring with hand-set brilliant-cut diamonds.\n58.",
                                                980.46, jewelry,
                                                "luxury_ring_on_a_sof_5157a06f-d2e3-4efc-ac3d-2a1ca8555750",
                                        1, "lightgray", parentProduct38.getProductNummer()));
                productDAO.addProduct(
                                new Product("Ring 21F0 | 50",
                                                "18-karat gold ring with hand-set brilliant-cut diamonds.\n50.",
                                                1748.6, jewelry,
                                                "luxury_ring_on_a_sof_5157a06f-d2e3-4efc-ac3d-2a1ca8555750",
                                        1, "lightgray", parentProduct38.getProductNummer()));

        Product parentProduct39 = productDAO.addProduct(new Product("Ring B392 | 58",
                                "18-karat gold ring with hand-set brilliant-cut diamonds.\n58.", 1515.51, jewelry,
                        "luxury_ring_on_a_sof_b27d558c-1ba7-4845-917e-384bddaeee7e", 1, "lightgray"));
        productDAO.addProduct(new Product("Ring B392 | 52",
                                "18-karat gold ring with hand-set brilliant-cut diamonds.\n52.", 1450.73, jewelry,
                        "luxury_ring_on_a_sof_b27d558c-1ba7-4845-917e-384bddaeee7e", 1, "lightgray",
                        parentProduct39.getProductNummer()));

        productDAO.addProduct(new Product("Skis 933A | 185 cm",
                                "Lightweight carbon-fibre skis for advanced alpine skiing.\n185 cm.", 1065.91,
                                sport,
                        "luxury_set_of_skis_o_002d3a53-1b73-4d22-9d8e-429796e3f67b", 1, "lightgray"));

        Product parentProduct40 = productDAO.addProduct(new Product("Skis 6224 | 165 cm",
                                "Lightweight carbon-fibre skis for advanced alpine skiing.\n165 cm.", 1146.95,
                                sport,
                        "luxury_set_of_skis_o_5c19e1fe-e7b2-4ec1-9aa3-13e8f1d2f69f", 1, "lightgray"));
        productDAO.addProduct(new Product("Skis 6224 | 185 cm",
                                "Lightweight carbon-fibre skis for advanced alpine skiing.\n185 cm.", 965.86,
                                sport,
                        "luxury_set_of_skis_o_5c19e1fe-e7b2-4ec1-9aa3-13e8f1d2f69f", 1, "lightgray",
                        parentProduct40.getProductNummer()));

        productDAO.addProduct(new Product("Skis 3182 | 175 cm",
                                "Lightweight carbon-fibre skis for advanced alpine skiing.\n175 cm.", 837.43,
                                sport,
                        "luxury_set_of_skis_o_ee1e0be5-d719-46b2-9374-2887f2a2a1c2", 1, "lightgray"));

        Role adminRole = new Role("ROLE_ADMIN");
        Role userRole = new Role("ROLE_USER");

        this.roleRepository.save(adminRole);
        this.roleRepository.save(userRole);

        CustomUser adminUser = new CustomUser("admin@gmail.com", passwordEncoder.encode("Welkom01!"), adminRole);

        this.userRepository.save(adminUser);
    }

    private void seedOrders(){
        Date now = new Date();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -2); // 1 dag terug
        Date yesterday = cal.getTime();

        OrderEntity orderEntity = new OrderEntity("admin@gmail.com", now, 19000, "aaaa", "1234ab", "bbbb", "cccc", true);
        this.orderEntityRepository.save(orderEntity);
        ProductOrder productOrder = new ProductOrder("Cartier Love Bracelet", 3, 21450, orderEntity);
        ProductOrder productOrder2 = new ProductOrder("Tiffany & Co. T Wire Bracelet", 2, 6900, orderEntity);
        this.productOrderRepository.save(productOrder);
        this.productOrderRepository.save(productOrder2);

        OrderEntity orderEntity2 = new OrderEntity("admin@gmail.com", now, 19000, "aaaa", "1234ab", "bbbb", "cccc", false);
        this.orderEntityRepository.save(orderEntity2);
        ProductOrder productOrder3 = new ProductOrder("Cartier Love Bracelet", 3, 21450, orderEntity2);
        ProductOrder productOrder4 = new ProductOrder("Tiffany & Co. T Wire Bracelet", 2, 6900, orderEntity2);
        this.productOrderRepository.save(productOrder3);
        this.productOrderRepository.save(productOrder4);

        ReturnRequest returnRequest = new ReturnRequest(orderEntity, "PENDING", now, "admin@gmail.com");
        this.returnRequestRepository.save(returnRequest);
        ReturnItem returnItem = new ReturnItem(returnRequest, "Cartier Love Bracelet", 3, 21450, "beschadigd");
        ReturnItem returnItem1 = new ReturnItem(returnRequest, "Tiffany & Co. T Wire Bracelet", 2, 6900, "beschadigd");
        this.returnItemRepository.save(returnItem);
        this.returnItemRepository.save(returnItem1);
    }
}
