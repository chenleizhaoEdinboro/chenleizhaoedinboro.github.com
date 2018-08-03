package Pages;

import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class FEPage {
	public WebDriver PageDriver;

	public FEPage(WebDriver driver) {
		this.PageDriver = driver;
		PageFactory.initElements(PageDriver, this);
	}
	// new home page SWAT team
	@FindBy(how = How.XPATH, using = "//div[contains(@class,'m-banner') and contains(@class,'lslide')]")
	public List<WebElement> heroSectionImg;

	@FindBy(how = How.XPATH, using = "//a[contains(@class,'lSNext')]")
	public List<WebElement> next;


	@FindBy(how = How.XPATH, using = "//*[@id='js-sliderEspot-1']")
	public List<WebElement> SecEspotNext;


	@FindBy(how = How.XPATH, using = "//ul[contains(@class,'lSPager')]/li")
	public List<WebElement> pager;

	//	@FindBy(how = How.XPATH, using = "//div[contains(@class,'m-espot') and contains(@class,'lslide')]")
	//	public List<WebElement> espot;

	@FindBy(how = How.XPATH, using = "//div[@id='js-sliderEspot-1']")
	public List<WebElement> espot;

	//	@FindBy(how = How.XPATH, using = "//div[contains(@class,'o-sliderEspot') and contains(@class,'-no-slider')]/div/div[contains(@class,'m-espot')]")
	//	public List<WebElement> espotNoSlider;


	@FindBy(how = How.XPATH, using = "//div[@class='m-banner -copyCentered']")
	public WebElement secondHeroImg;

	@FindBy(how = How.XPATH, using = "//div[contains(@class,'m-banner') and contains(@class,'-copyCentered')]/div[2]")
	public List<WebElement> secondHeroImgDiv;

	// new deals page SWAT Team
	@FindBy(how=How.XPATH,using="//div[contains(@class,'m-navBar__item')]")
	public List<WebElement> navItems;

	// new deals page SWAT Team
	@FindBy(how=How.XPATH,using="//a[contains(@class,'m-navBar__link')]")
	public List<WebElement> activenavItems;

	@FindBy(how=How.XPATH,using="//div[contains(@class,'-singleLayout')]//div[@class='o-productSummary']")
	public WebElement singleLayoutSummary;


	@FindBy(how=How.XPATH,using="//div[contains(@class,'-singleLayout')]")
	public WebElement singleLayout;

	@FindBy(how=How.XPATH,using="//div[contains(@class,'-singleLayout')]//div[@class='o-productSummary']//div[@class='o-productLayout__carousel']//div[@id='productCarousel-1']")
	public WebElement carousel1;


	@FindBy(how=How.XPATH,using="//div[contains(@class,'-doubleLayout')]//div[contains(@class,'o-productSummary')]")
	public List<WebElement> doubleLayout;

	@FindBy(how=How.XPATH,using="//div[contains(@class,'-fourLayout')]//div[contains(@class,'o-productSummary')]")
	public List<WebElement> fourLayout;

	//SWAT team deals page accessories tab	

	@FindBy(how=How.XPATH,using="//div[contains(@class,'o-accessoryLayout')]")
	public WebElement accessoryLayout;

	@FindBy(how=How.XPATH,using="//ul[@class='slick-dots']")
	public List<WebElement> slickyDots;

	@FindBy(how=How.XPATH,using="//div[contains(@class,'slick-list') and contains(@class,'draggable')]")
	public List<WebElement> slickdraggable;

	@FindBy(how=How.XPATH,using="//button[@id='addToCart']")
	public WebElement deals_addToCart;

	@FindBy(how=How.XPATH,using="//button[contains(@class,'addToCartPopupButton')]")
	public WebElement dealsPopup_addToCart;

	@FindBy(how=How.XPATH,using="//h2[@class='addedToCartMode']")
	public WebElement addedToCartMsg;	

	@FindBy(how=How.XPATH,using="//div[@class='addToCardModeImageAndDes']")
	public WebElement addToCardModeImageAndDes;

	@FindBy(how=How.XPATH,using="//button[@id='cboxClose']")
	public WebElement cBoxClose;

	// create legal component
	@FindBy(how=How.XPATH,using="//li[contains(@class,'accordion-item')]")
	public WebElement legalComponent;

	@FindBy(how=How.XPATH,using="//div[@class='accordion-content']")
	public WebElement legalComponentContent;


	//shabeena : BRoWSE-137
	@FindBy(how = How.ID, using = "smb-login-button") 
	public WebElement SMB_LoginButton; 
	@FindBy(how = How.XPATH, using = "//input[(@id='login.email.id')]")
	public WebElement SMB_UserNameTextBox; 
	@FindBy(how = How.XPATH, using = "//input[(@id='login.password')]") 
	public WebElement SMB_PasswordTextBox;
	@FindBy(how = How.XPATH, using = "//button[(@class='button-called-out signInForm-submitButton')]")
	public WebElement SMB_SignInButton;	
	@FindBy(how=How.XPATH,using="//div[@class='facetedResults-item only-allow-small-pricingSummary']//div[@class='facetedResults-body']")
	public List<WebElement> subscription;
	@FindBy(how=How.XPATH,using="//div[contains(@class,'facetedResults-item')]") 
	public List<WebElement> billingCycle; 
	@FindBy(how=How.XPATH,using="//a[(@class='button-called-out button-full facetedResults-cta')]")
	public List<WebElement> viewButton; 
	@FindBy(how=How.XPATH,using="//p[(@class='accessoriesDetail-priceBlock-price')]")
	public WebElement billingCycleOnPDP; 
	@FindBy(how=How.XPATH,using="//tr[(@id='addToCartRow')]//button[(@class='button-called-out button-full')]") 
	public WebElement addToCartButtonOnPDP; 
	@FindBy(how=How.XPATH,using="//div[(@class='webprice')]")
	public WebElement windowAddToCartBillingCycleOnPDP; 
	@FindBy(how=How.XPATH,using="//button[(@class='button-called-out button-full addToCart addToCartMode addToCartPopupButton cboxElement')]")
	public WebElement windowAddToCartButtonOnPDP;
	@FindBy(how=How.XPATH,using="//a[(@class='button-called-out continueShopCTA addedToCart')]") 
	public WebElement windowGoToCartOnPDP; 
	@FindBy(how=How.XPATH,using="//dl[(@class='pricing-info-value cart-summary-pricing-webPrice-label salePrice')]") 
	public WebElement billingCycleOnCartPage; 
	@FindBy(how=How.XPATH,using="//a[(@class='cart-checkoutButtons-checkout button-called-out-positive fs1 qa_ProceedToCheckout_Button')]") 
	public WebElement proceedToCheckoutOnCartPage; 
	@FindBy(how=How.XPATH,using="//div[(@class='base-price')]") 
	public WebElement billingCycleOnPaymentPage; 
	@FindBy(how=How.XPATH,using="//button[(@class='shipping_section_continue_button')]") 
	public WebElement continueButtonPaymentPage; 
	@FindBy(how=How.XPATH,using="//div[(@class='base-price')]") 
	public WebElement billingCycleAtOrderSummery; 
	@FindBy(how=How.XPATH,using="div[@id='c-content']")
	public List<WebElement> cardType; 
	@FindBy(how=How.XPATH,using="div[@id='c-content']")
	public List<WebElement> cardNumber;
	@FindBy(how=How.XPATH,using="div[@id='c-content']") 
	public List<WebElement> expiryMonth; 
	@FindBy(how=How.XPATH,using="div[@id='c-content']]") 
	public List<WebElement> expiryYear; 
	@FindBy(how=How.XPATH,using="div[@id='c-content']")
	public List<WebElement> securityCode; 
	@FindBy(how=How.XPATH,using="//div[(@class='checkoutForm-formGroup checkoutForm-formGroup-cardName checkoutForm-formGroup-required checkoutForm-formGroup-cardName4CreditCard-div')]") 
	public WebElement cardHoldername; 
	@FindBy(how=How.XPATH,using="//button[(@class='billing_section_continue_button')]") 
	public WebElement continueToReviewButton;


	//---homepage Master header


	@FindBy(how = How.XPATH, using = "//ul[contains(@class,'prd_Menu')]/li/a/span")
	public List<WebElement> headerMenuList;

	@FindBy(how = How.XPATH, using = "//li[contains(@class,'product')]/a/span")
	public WebElement ProductsLink;

	@FindBy(how = How.XPATH, using = "//li[contains(@class,'product_menu _no_small_screen')]")
	public WebElement ProductsLinkNav;

	//			@FindBy(how = How.XPATH, using = "//ul[contains(@class,'menu_2')]/li/a[contains(@href,'laptops') or contains(@href,'notebooks') or contains(@href,'Laptops')]")
	//			public WebElement LaptopLink;

	@FindBy(how = How.XPATH, using = "//ul[contains(@class,'menu_2')]/li/a")
	public List<WebElement> productsList;

	//			@FindBy(how = How.XPATH, using = "//ul[contains(@class,'series_menu'OR @class,'level3_custom_menu')]/li")
	//			public List<WebElement> subLinks;


	@FindBy(how = How.XPATH, using = "//ul[@class='series_menu']/li[@class='active']/div[@class='megaMenu_contentSection']/ul[@class='subseries_data']/li/ol[@class='seriesListings']/li[@class='seriesListings-itemContainer']")
	public List<WebElement> seriesListing;

	@FindBy(how = How.XPATH, using = "//li[contains(@class,'deals')]/a")
	public WebElement dealsLink;

	@FindBy(how = How.XPATH, using = "//li[contains(@class,'deals')]/div[@class='megaMenu_wrapper']/div/ul[1]/li")
	public List<WebElement> dealsList;

	@FindBy(how = How.XPATH, using = "//a[@class='espotLink']")
	public WebElement featureDeal;

	@FindBy(how = How.XPATH, using = "//li[contains(@class,'support')]/a")
	public WebElement supportLink;

	@FindBy(how = How.XPATH, using = "//ul/li[contains(@class,'support')][1]/div[@class='megaMenu_wrapper']/div/ul/li")
	public List<WebElement> supportList;

	@FindBy(how = How.XPATH, using = "//ul/li[contains(@class,'support')][2]/a")
	public WebElement solutionLink;

	@FindBy(how = How.XPATH, using = "//ul/li[contains(@class,'support')][2]/div[@class='megaMenu_wrapper']/div/ul/li")
	public List<WebElement> solutionList;

	@FindBy(how = How.XPATH, using = "(//ul[@class='megaMenu_mainSection_list'])[1]/li")
	public List<WebElement> laptopLinks;

	@FindBy(how = How.XPATH, using = "(//ul[@class='megaMenu_mainSection_list'])[6]/li")
	public List<WebElement> accessoriesAndMonitorsLinks;

	@FindBy(how = How.XPATH, using = "(//ul[@class='megaMenu_mainSection_list'])[7]/li")
	public List<WebElement> serversAndStorageLinks;

	@FindBy(how = How.XPATH, using = "(//ul[@class='megaMenu_mainSection_list'])[8]/li")
	public List<WebElement> serviceAndWarrantyLinks;

	@FindBy(how = How.XPATH, using = "(//ul[@class='megaMenu_mainSection_list'])[2]/li")
	public List<WebElement> desktopLinks;

	@FindBy(how = How.XPATH, using = "(//ul[@class='megaMenu_mainSection_list'])[3]/li")
	public List<WebElement> workstationLinks;

	@FindBy(how = How.XPATH, using = "(//ul[@class='megaMenu_mainSection_list'])[4]/li")
	public List<WebElement> phoneTabletsLinks;

	@FindBy(how = How.XPATH, using = "(//ul[@class='megaMenu_mainSection_list'])[5]/li")
	public List<WebElement> gamingLinks;

	@FindBy(how = How.XPATH, using = "//div[contains(@class,'nav') and contains(@class,'desktop')]")
	public WebElement mastheadWidth;

	//div[contains(@class,'nav') and contains(@class,'desktop')]

	//---homepage Master header	Mobile

	@FindBy(how = How.XPATH, using = "//a[@class='main_Menu_icon']")
	public WebElement mainmenuIcon;

	// CONTENT-14 and SWAT-1455
	@FindBy(how = How.XPATH, using = "//div[@id='rotating-hero-banner']//div[contains(@class,'premium-banner-container')][1]")
	public WebElement FirstBanner;

	@FindBy(how = How.XPATH, using = "//a[@class='a-link -bannerLink m-banner__copy__text']")
	public List<WebElement> firstCTA;

	@FindBy(how = How.XPATH, using = "//a[@class='premium-links qa_secondary_cta']")
	public WebElement secondCTA;

	@FindBy(how = How.XPATH, using = "//div[@class='brand-story item premium-banner-container premium-banner-one']")
	public WebElement secondBanner;

	//---Global search

	//NA-21218
	@FindBy(how = How.XPATH, using = "//input[contains(@class,'nxtGen_searchBar')]")
	public WebElement searchbar;

	@FindBy(how = How.XPATH, using = "//button[contains(@class,'Qa_Clear_Text_icon')]")
	public WebElement searchclearIcon;

	@FindBy(how = How.XPATH, using = "//div[@class='taqueryresults']")
	public WebElement suggestedResultsWindow;

	@FindBy(how = How.XPATH, using = "//button[contains(@class,'Qa_Search_icon')]")
	public WebElement seachIcon;

	@FindBy(how = How.XPATH, using = ".//*[@id='SearchPageTitle']/div/div")
	public WebElement seachresultTitle;

	@FindBy(how = How.XPATH, using = "//div[contains(@class,'Qa_Suggested_Searches_Section')]")
	public WebElement suggestedSearches;

	@FindBy(how = How.XPATH, using = "//div[contains(@class,'Qa_Recommended_Results_Section')]")
	public WebElement recommendedResultSection;

	@FindBy(how = How.XPATH, using = "//a[contains(@class,'Qa_Search_in_Products')]")
	public WebElement suggestedProductsLink;

	@FindBy(how = How.XPATH, using = "//a[@id='productsSection']/span")
	public WebElement suggestedProductsText;

	@FindBy(how = How.XPATH, using = "(.//*[@id='heading']/span[contains(@class,'Qa_ExpandOrCollapse_Categories')])[1]")
	public WebElement suggestedProductsTextSearch;

	@FindBy(how = How.XPATH, using = "//a[contains(@class,'Qa_Search_in_Accessories')]")
	public WebElement suggestedAccessoriesLink;

	@FindBy(how = How.XPATH, using = "//a[contains(@class,'Qa_Search_in_News')]")
	public WebElement suggestedNewsLink;

	@FindBy(how = How.XPATH, using = "//a[contains(@class,'Qa_Search_in_Support')]")
	public WebElement suggestedSupportLink;

	@FindBy(how = How.XPATH, using = ".//*[@id='supportExpand']/span[2]")
	public List<WebElement> minimizeList;

	//	@FindBy(how = How.XPATH, using = "(//div[@id='searchProducts'])[2]//span[@id='supportExpand']/span[1]")
	//	public WebElement exploreList;

	@FindBy(how = How.XPATH, using = "(//div[@id='searchProducts'])[2]//span[@id='supportExpand']/i")
	public WebElement exploreList;



	@FindBy(how = How.XPATH, using = "(//ul[@class='ta-search-result-wrapper'])/li")
	public List<WebElement> recommendedResults;

	@FindBy(how = How.XPATH, using = "//div[@class='ta-content']/div[1]")
	public WebElement recommendedResultText;

	@FindBy(how = How.XPATH, using = "//h1[@class='desktopHeader']")
	public WebElement recommendedResultsearchresultText;

	//DCG hide rating and Free shipping - CONTENT-290


	@FindBy(how = How.XPATH, using = "//ul[@class='ta-search-result-wrapper']//li[contains(@class,'ta-search-result')]")
	public List<WebElement> recommendedResultsRating;



	@FindBy(how = How.XPATH, using = "(//ul[contains(@class,'slider')])[1]/li")
	public List<WebElement> searchResults;

	@FindBy(how = How.XPATH, using = "//div[@data-type='product']")
	public WebElement Loadmore;

	@FindBy(how = How.XPATH, using = "(//li[@class='lastSlide'])[1]/div[@class='loadMoreButton product-card']/a[@class='loadButton']")
	public WebElement LoadmoreButton;

	@FindBy(how = How.XPATH, using = ".//*[@id='productsResults']/div/ul/li[21]/div/a[1]")
	public WebElement LoadmoreButton2;


	@FindBy(how = How.XPATH, using = "(//div[@class='loadMoreButton']/a[@class='loadButton'])[1]")
	public WebElement loadmoreButtonMobile;

	@FindBy(how = How.XPATH, using = "//div[@class='ta-price Qa_Product_StartingPrice']")
	public List<WebElement> recommendedresultPrice;


	@FindBy(how = How.XPATH, using = "//ul[@class='ta-search-result-wrapper']//li[contains(@class,'ta-search-result')]")
	public List<WebElement> recommendedresultSection;

	//Search results - Grid view

	@FindBy(how = How.XPATH, using = "//div[contains(@id,'facet-list-item')]")
	public List<WebElement> searchFilters;

	@FindBy(how = How.XPATH, using = "(//h3[@class='expandableHeading'])[1]")
	public WebElement processorFilter;

	@FindBy(how = How.XPATH, using = "//li[@id='facet4_2']")
	public WebElement processorFilterOption;

	@FindBy(how = How.XPATH, using = "//li[@id='facet4_2']/ol/li/label/span")
	public WebElement processorFilterOptionText;

	@FindBy(how = How.XPATH, using = "(//span[@class='facet-area-clear'])[2]")
	public WebElement selectedFilter;

	@FindBy(how = How.XPATH, using = "(//span[@id='facet-area-clear-all'])[2]")
	public WebElement clearAll;

	@FindBy(how = How.XPATH, using = "//select[contains(@class,'sortSection')]")
	public WebElement Sort;

	@FindBy(how = How.XPATH, using = "//*[@id='sortView']/div/select")
	public WebElement SortbyList;


	@FindBy(how = How.XPATH, using = "//div[@id='productsResults']//div[1]/div[2]/p[2]/span")
	public List<WebElement> productsPrice;

	@FindBy(how = How.XPATH, using = "//h3[@class='product-card__name']/a")
	public WebElement resultTitle;


	@FindBy(how = How.XPATH, using = "(//span[contains(@class,'Qa_Support_Tittle')])[4]")
	public WebElement accessories;

	@FindBy(how = How.XPATH, using = "(//span[contains(@class,'Qa_Support_Tittle')])[5]")
	public WebElement supportTitle;

	@FindBy(how = How.XPATH, using = "(//span[contains(@class,'Qa_NewsAndResources_Title')])")
	public WebElement newsresourceTitle;

	@FindBy(how = How.XPATH, using = "(//div[@id='gridView'])[2]")
	public WebElement gridView;

	@FindBy(how = How.XPATH, using = "(//div[@id='listView'])[2]")
	public WebElement listViewButton;


	@FindBy(how = How.XPATH, using = "(//div[@id='productListImages']//img[contains(@src,'.png')])[1]")
	public WebElement listViewThumbnail;

	@FindBy(how = How.XPATH, using = "(//span[@class='price__value'])[1]")
	public WebElement listViewprice;

	@FindBy(how = How.XPATH, using = "(//h3[@class='product-card__name qa_Product_Title'])[2]")
	public WebElement listProductTitle;

	@FindBy(how = How.XPATH, using = "(//span[@class='bv-rating-stars-container'])[2]")
	public WebElement listviewRating;

	@FindBy(how = How.XPATH, using = ".//*[@id='searchProducts']/div[3]/a[@class='loadButton']")
	public WebElement listviewLoadmoreButton;

	@FindBy(how = How.XPATH, using = "//input[@id='inputSearchText']")
	public WebElement searchBar;

	@FindBy(how = How.XPATH, using = ".//li[contains(@class,'no-results-found')]")
	public WebElement noResulttext;

	@FindBy(how = How.XPATH, using = "//li[@class='no-results-text1']")
	public WebElement sorryText;

	@FindBy(how = How.XPATH, using = "//li[@class='no-results-text1']")
	public WebElement suggestionTitle;

	@FindBy(how = How.XPATH, using = "//div[@class='recent-results-header']")
	public WebElement recentsearchTitle;

	@FindBy(how = How.XPATH, using = "(//h3[@class='product-card__name'])[1]")
	public WebElement recentsearchProdTitle;

	@FindBy(how = How.XPATH, using = "(//span[@class='bv-rating-stars-container'])[1]")
	public WebElement recentsearchRating;

	@FindBy(how = How.XPATH, using = ".//*[@id='productsResults']/div/div[1]/a[2]")
	public WebElement prodNxtButton;

	@FindBy(how = How.XPATH, using = ".//*[@id='productsResults']/div/div[1]/a[1]")
	public WebElement prodprevButton;

	//Global search - mobile
	@FindBy(how = How.XPATH, using = "//a[@id='searchKW']")
	public WebElement sugProdlinkMobile;

	@FindBy(how = How.XPATH, using = "//h1[@class='mobileHeader']")
	public WebElement recommendedResultsearchresultTextmobile;

	@FindBy(how = How.XPATH, using = "//span[@class='expandFilter']")
	public WebElement morefiltersMobile;

	@FindBy(how = How.XPATH, using = "//li[@id='facet4_0']/ol/li/label/span")
	public WebElement processorFilterOptionTextMobile;

	@FindBy(how = How.XPATH, using = "//li[@id='facet4_0']")
	public WebElement processorFilterOptionMobile;

	@FindBy(how = How.XPATH, using = "//*[@id='facet1_0']/ol/li/label/span")
	public WebElement selectedfilterMobile;

	@FindBy(how = How.XPATH, using = "(//div[@id='listView'])[1]")
	public WebElement listViewButtonMobile;

	@FindBy(how = How.XPATH, using = "(//span[@class='mobile-facet-choices'])[2]")
	public WebElement processorFacetSub;

	@FindBy(how = How.XPATH, using = "(//h3[@class='product-card__name qa_Product_Title'])[1]")
	public WebElement listProductTitleMobile;

	@FindBy(how = How.XPATH, using = "(//span[@class='bv-rating-stars-container'])[1]")
	public WebElement listviewRatingMobile;

	@FindBy(how = How.XPATH, using = ".//*[@id='searchAccessories']/div[3]/a[@class='loadButton']")
	public WebElement listviewLoadmoreButtonAccessoriesMobile;

	//UX validation checklist

	//htmlImgsrc_Header(required:false){$("div",class:contains("body-inner-wrapper")).find("header").find("img",class:contains("lazyLoadAfterDOMInteractive"))}

	@FindBy(how = How.XPATH, using = "(//div[@class='body-inner-wrapper'])//header//img")
	public List<WebElement> imgsrcLazyloadHeader;

	@FindBy(how = How.XPATH, using = "//div[contains(@class,'mainContent ')]//img")
	public List<WebElement> imgsrcLazyloadMain;

	@FindBy(how = How.XPATH, using = "//div[contains(@class,'mainFooter')]//img")
	public List<WebElement> imgsrcLazyloadFooter;


	@FindBy(how = How.XPATH, using = "//div[@id='_gwdmetrics']")
	public WebElement jsPopup;


	//Cart page
	@FindBy (how=How.ID, using="view-customize")
	public WebElement view_CustomizeButton;
	@FindBy(how=How.XPATH,using="//li[contains(@class,'tabbedBrowse-productListing-container')]//button[@id='addToCartButtonTop']")
	public List<WebElement> addOrCustomizeButton;
	@FindBy(how=How.XPATH,using="//dl[@class='pricingSummary-details']")
	public List<WebElement> pricingSummaryDetails;
	@FindBy(how=How.XPATH,using="//li[contains(@class,'tabbedBrowse-productListing-container')]//dd[@class='saleprice pricingSummary-details-final-price']")
	public WebElement salePriceOnSubSeries;
	@FindBy(how=How.XPATH,using="//li[contains(@class,'tabbedBrowse-productListing-container')]")
	public List<WebElement> itemDiscountOnSubSeries;
	@FindBy(how=How.XPATH,using="//li[contains(@class,'tabbedBrowse-productListing-container')][1]//dd[contains(@class,'saleprice pricingSummary-priceList-value ls-has-discount')][1]")
	public List<WebElement> itemWebPriceOnSubSeries;
	@FindBy(how=How.XPATH,using="//li[contains(@class,'tabbedBrowse-productListing-container')]//h3[@class='tabbedBrowse-productListing-title']")
	public List<WebElement> prouctTitleOnSubSeries;
	@FindBy(how=How.XPATH,using="//button[contains(@class,'AddToCartButton')]/span[contains(text(),'Add to Cart')]")
	public WebElement addToCartButtonOnConfigurator;
	@FindBy(how=How.XPATH,using="//div[contains(@class,'promotion-price')]//div[contains(@class,'value final-price')]")
	public WebElement yourPriceOnConfigurator;
	//Web Element on Cart Page
	@FindBy(how=How.XPATH,using="//h4[@class='cart-item-title qa_Product _Title']/div[@class='itemName']")
	public WebElement itemNameOnCartPage;
	@FindBy(how=How.XPATH,using="//p[contains(@class,'cart-item-partNumber')]//span[3]")
	public WebElement itemPartNumber;
	@FindBy(how=How.XPATH,using="//div[@class='cart_item_imgDiv']/img")
	public WebElement itemImage;
	@FindBy(how=How.XPATH,using="//dl[contains(@class,'cart-item-pricing-and-quantity-Price-amount')]")
	public WebElement itemWebPriceOnCartPage;
	@FindBy(how=How.XPATH,using="//dl[contains(@class,'cart-summary-pricing-webPrice-label')]")
	public WebElement itemSalePriceOnCartPage;
	@FindBy(how=How.XPATH,using="//div[@class='cart-summary-totalSavings']//span[contains(@class,'cart-summary-totalDiscounts')]")
	public WebElement itemDiscountOnCartPage;

	@FindBy(how=How.XPATH,using="//div[@id='inputOuter']//input[@id='quickOrderProductId']")
	public WebElement partNumberInputText;
	@FindBy(how=How.XPATH,using="//div[@id='quickAddInput']//button[contains(@class,'qa_Submit_Button')]")
	public WebElement submitButtonCartPage;
	@FindBy(how=How.XPATH,using="//div[@class='editTextBtn']")
	public WebElement editButtonCartPage;
	@FindBy(how=How.XPATH,using="//div[@class='cart-summary']//dd//span[@class='price-calculator-cart-summary-subTotalWithoutCoupon']")
	public WebElement itemSubtotal;
	@FindBy(how=How.XPATH,using="//div[@class='cart-summary']//dd//span[contains(@class,'qa_estimatedTotal_Price')]")
	public WebElement estimatedTotal;
	@FindBy(how=How.XPATH,using="//form[@class='cart-item-pricing-and-quantity-form']//input[contains(@class,'qty iform qa_QuantityUpdate')]")
	public WebElement quantityInputText;
	@FindBy(how=How.XPATH,using="//form[@class='cart-item-pricing-and-quantity-form']//input[contains(@class,'cart-item-pricing-and-quantity-form-button')]")
	public WebElement updateLink;
	@FindBy(how=How.ID,using="RemoveProduct_0")
	public WebElement removeLink;
	@FindBy(how=How.XPATH,using="//li[@class='qa_Edit_link']//a")
	public WebElement editLink;
	@FindBy(how=How.XPATH,using="//div[contains(@class,'stackableSection')][1]//li[contains(@class,'warranty-option')][2]//div[@class='btnText']//span[@class='stackablePriceLabel']")
	public WebElement warrantyOption;
	@FindBy(how=How.XPATH,using="//div[@class='alert positive']")
	public WebElement alertPositiveCartPage;
	@FindBy(how=How.XPATH,using="//div[@class='popup-itemlimit']")
	public WebElement itemLimitPopup;
	@FindBy(how=How.XPATH,using="//div[@class='cart-item']")
	public List<WebElement> cartItem;
	@FindBy(how=How.XPATH,using="//dd[@class='cart-item-addedItem-price']")
	public WebElement cartItemAddedItemPrice;
	@FindBy(how=How.XPATH,using="//dt[@class='cart-item-addedItem-title']")
	public WebElement cartItemAddedItemTitle;
	@FindBy(how=How.XPATH,using="//div[@id='cart_empty']/h3")
	public WebElement cartEmptyLabel;
	@FindBy(how=How.XPATH,using="//div[contains(@class,'stackableSection open')][1]//li[contains(@class,'warranty-option')][2]//div[@class='btnText']//span[@class='stackablePriceLabel']")
	public WebElement mobileWarrantyOption;
	@FindBy(how=How.XPATH,using="//ol[@class='stepsItem']//li[contains(@class,'qa_stackablewarrantyTab')]/a")
	public WebElement warrantyTab;
	@FindBy(how=How.XPATH,using="//button[contains(@class,'qa-configurator-sectionExpandButton')]")
	public WebElement changeButton;
	//Web Element of SignIn Page
	@FindBy(how=How.XPATH,using="//li[@id='myAccount']/a")
	public WebElement myAccountLink;
	@FindBy(how=How.XPATH,using="//div[@class='link_text' and contains(text(),'Sign In')]")
	public WebElement signInLink;
	@FindBy(how=How.ID,using="login.email.id")
	public WebElement username;
	@FindBy(how=How.ID,using="login.password")
	public WebElement password;
	@FindBy(how=How.XPATH,using="//button[contains(@class,'signInForm-submitButton')]")
	public WebElement signInButton;
	//Web Element of SAveCart Page
	@FindBy(how=How.XPATH,using="//li[@id='cartIcon']/a[@class='cartlink']")
	public WebElement cartLink;
	@FindBy(how=How.XPATH,using="//li[contains(@class,'SaveCart_link ')]/a")
	public WebElement saveForLaterLink;
	@FindBy(how=How.XPATH,using="//div[contains(@class,'savedCart-items')]")
	public WebElement savedCartItemSection;
	@FindBy(how=How.XPATH,using="//h3[contains(@class,'Text_SaveCart')]")
	public WebElement saveCartItemLabel;
	@FindBy(how=How.XPATH,using="//p[contains(@class,'saveCart _PartNumber')]/span")
	public WebElement saveCartPartNumber;
	@FindBy(how=How.XPATH,using="//dl[contains(@class,'saveCart_WebPrice')]")
	public WebElement saveCartWebPrice;
	@FindBy(how=How.XPATH,using="//li[contains(@class,'saveCart_Remove_link')]")
	public WebElement saveCartRemoveLink;
	@FindBy(how=How.XPATH,using="//li[contains(@class,'saveCart_AddToCart_link')]")
	public WebElement saveCartAddToCartLink;
	@FindBy(how=How.XPATH,using="//li[contains(@class,'saveCart_AddToCart_link')]//a")
	public WebElement mobilesaveCartAddToCartLink;
	@FindBy(how=How.XPATH,using="//a//img[contains(@src,'printer')]")
	public WebElement printerLink;
	@FindBy(how=How.XPATH,using="//a//img[contains(@src,'email')]") 
	public WebElement emailLink;
	@FindBy(how=How.XPATH,using="//a//img[contains(@src,'trashcan')]") 
	public WebElement trashLink;
	@FindBy(how=How.XPATH,using="//div[@class='popup_arrow_box']") 
	public WebElement trashPopup;
	@FindBy(how=How.XPATH,using="//div/input[@class='button-standard button-small popup_button'][1]")
	public WebElement saveLaterButtonInPopup;
	@FindBy(how=How.XPATH,using="//a[contains(@class,'fancybox-close')]")
	public WebElement saveLaterPopupCloseLink;
	@FindBy(how=How.XPATH,using="//div/input[@class='button-standard button-small popup_button'][2]")
	public WebElement deleteCartButtonInPopup;
	@FindBy(how=How.XPATH,using="//a//img[contains(@src,'save-icon')]") 
	public WebElement saveiconLink;
	@FindBy(how=How.XPATH,using="//div[@id='saved-cart-div']")
	public WebElement cartNamePopup;
	@FindBy(how=How.XPATH,using="//div[@class='savedCartBody']")
	public WebElement savedCartBody;
	@FindBy(how=How.XPATH,using="//textarea[@id='realsavecartname']")
	public WebElement realsavecartnameInputText;
	@FindBy(how=How.XPATH,using="//input[@id='addToCartButtonTop']")
	public WebElement saveCartButton;
	@FindBy(how=How.XPATH,using="//table[@id='cntTable']/tbody/tr/td[2]")
	public WebElement cartNameInCartHistory;
	@FindBy(how=How.XPATH,using="//table[@id='cntTable']/tbody/tr/td[4]")
	public WebElement TotalInCartHistory;


	// --- Accessory PDP 
	@FindBy(how = How.XPATH, using ="//div[contains(@class,'m-productHeaderTitle__name')]")
	public WebElement productTitle; 
	@FindBy(how = How.XPATH, using = "//div[@class='m-productHeaderTitle__partnumber']")
	public WebElement partNumber; 
	@FindBy(how = How.XPATH, using ="//div[@class='o-productLayout__carousel']//div[contains(@class,'slick-active')]//img[contains(@class,'m-carousel__image')]")
	public WebElement productImage; 
	@FindBy(how = How.XPATH, using ="//ul[@class='slick-dots']/li")
	public List<WebElement> slickDots;
	@FindBy(how = How.XPATH, using ="//div[@class='m-priceSummary']//span[contains(@class,'a-start')]")
	public WebElement productPrice; 
	@FindBy(how = How.XPATH, using = "//div[@class='m-shipping__label']")
	public WebElement shippingLabel; 
	@FindBy(how = How.XPATH, using = "//div[@class='m-review']")
	public WebElement review;
	@FindBy(how = How.XPATH, using = "//a[contains(@class,'m-navBar__link')]")
	public List<WebElement> navBarItem;
	@FindBy(how = How.XPATH, using = "//span[@class='m-navBar__title']")
	public List<WebElement> navBarTitle;
	@FindBy(how = How.XPATH, using = "//div[contains(@class,'tabs-content__item tabs-content')]/h2")
	public List<WebElement> contentTitle;

	
	//Accessories compare
	
	@FindBy(how = How.XPATH, using = "//h3[@class='tabbedBrowse-productListing-title']")
	public List<WebElement> accessoriesproductTitle;
	
	@FindBy(how = How.XPATH, using = "//dl/dd[@class='saleprice pricingSummary-details-final-price']")
	public List<WebElement> salePrice;
	
	@FindBy(how = How.XPATH, using = "//div[@class='comparecheckbox']/label")
	public List<WebElement> compareCheckBox;
	
	@FindBy(how = How.XPATH, using = "//div[@class='productSummaryInfo']")
	public List<WebElement> popupProduct;
	
	@FindBy(how = How.XPATH, using = "//span[@class='productinfo']")
	public List<WebElement> popupProductTitle;
	
	@FindBy(how = How.XPATH, using = "//div[contains(@class,'compare-prompt-quantity-limit')]")
	public WebElement minQuantityMsg;
	
	@FindBy(how = How.XPATH, using = "//div[@class='compareModels']/div/p/button[contains(@class,'button-called-out')]")
	public WebElement compareButton;
	
	@FindBy(how = How.XPATH, using = "//div[@class='tabbedBrowse-productListing']")
	public List<WebElement> prodList;
	
	@FindBy(how = How.XPATH, using = "//button[contains(@class,'js-next button-called-out-alt')]")
	public WebElement nextButton;
	
	@FindBy(how = How.XPATH, using = "//dd[contains(@class,'saleprice')]")
	public List<WebElement> allPrices;
	
	@FindBy(how = How.XPATH, using = "//button[contains(@class,'js-previous')]")
	public WebElement prevButton;
	
	@FindBy(how = How.XPATH, using = "//div[@class='tabbedBrowse-productListing-footer-button-holder tabbedBrowse-productListing-footer-button-holder-add-margin-temp']//button[@id='addToCartButtonTop']")
	public List<WebElement> customizeButton;
	
	@FindBy(how = How.XPATH, using = "//h2[contains(@class,'tabbedBrowse-title qa')]")
	public WebElement customizeHeader;
	
	@FindBy(how = How.XPATH, using = "//div[@class='compare-prompt-failed-add compare-propmt-shown']")
	public WebElement fourItemsMsg;
}
