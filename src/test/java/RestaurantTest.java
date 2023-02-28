import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;

class RestaurantTest {


    @Mock
    Restaurant restaurant;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @BeforeEach
    private void declareRestaurant(){
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant =new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);


    }
    //REFACTORED ALL THE REPEATED LINES OF CODE

    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    //-------FOR THE 2 TESTS BELOW, YOU MAY USE THE CONCEPT OF MOCKING, IF YOU RUN INTO ANY TROUBLE
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){
        Restaurant myRes = Mockito.spy(restaurant);
        LocalTime now = LocalTime.parse("13:00:00");
        Mockito.when(myRes.getCurrentTime()).thenReturn(now);

        Assertions.assertTrue(myRes.isRestaurantOpen());
        //COMPLETED
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){
        Restaurant myRes = Mockito.spy(restaurant);
        LocalTime now = LocalTime.parse("23:00:00");
        Mockito.when(myRes.getCurrentTime()).thenReturn(now);

        Assertions.assertFalse(myRes.isRestaurantOpen());
        //COMPLETED

    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1(){
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie",319);
        assertEquals(initialMenuSize+1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize-1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {
        assertThrows(itemNotFoundException.class,
                ()->restaurant.removeFromMenu("French fries"));
    }
    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    // The Method calculateTotalOrderValue is a part of the restaurant class since it requires the internal menu variables to function
    //<<<<<<<<<<<<<<<<<<<<<<<BILL>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    @Test
    public void when_no_item_is_passed_total_should_be_zero(){
        //List of selected items
        List<String> orderItems = new ArrayList<>();

        //Bill Amount
        int orderValue = this.restaurant.calculateTotalOrderValue(orderItems);

        Assertions.assertEquals(0,orderValue);


    }

    @Test
    public void when_items_are_passed_total_should_return_sum_of_individual_price(){
        // Menu initialized = [{"Sweet corn soup",119},{"Vegetable lasagne", 269}]

        //Add New Items

        this.restaurant.addToMenu("Coca Cola",10);
        this.restaurant.addToMenu("French Fries",100);

        List<String> orderItems = new ArrayList<>();
        orderItems.add("Coca Cola");
        orderItems.add("Vegetable lasagne");

        //Bill
        int orderValue= this.restaurant.calculateTotalOrderValue(orderItems);

        //Assert
        // Total Price = 10+269 = 279

        Assertions.assertEquals(10+269,orderValue);
    }











    //<<<<<<<<<<<<<<<<<<<<<<<BILL>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
}