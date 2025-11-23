import unittest

class TestDivide(unittest.TestCase):
    def test_both_positive(self):
        a, b = 4, 2
        answer = 2
        self.assertEqual(divide(a, b), answer)

    def test_both_negative(self):
        a, b = -4, -2
        answer = 2
        self.assertEqual(divide(a, b), answer)

    # один отриц 
    # второй отриц

    # дробные и целые
    # первый большой
    # второй маленький
    # оба маленькие 
    # оба большие 

    def test_zero_zero(self):
        a, b  = 0, 0
        answer = 0
        self.assertEqual(divide(a, b), answer)

    def test_zero_notZero(self):
        a, b = 0, 5
        answer = 0
        self.assertEqual(divide(a, b), answer)

    
    def test_notZero_zero(self):
        a, b = 5, 0
        self.assertEqual(divide(a, b), "Can't divide by zero")
    
    