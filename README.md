# Cloud native training: practical case

This is a workshop that combines some cloud native theoretical and practical modules. It is focused on a Spring Boot application.

## Business Case

Our fictional company ``ezGroceries`` wants to provide all sorts of Mobile apps to help people during their grocery shopping.

People can look up cocktails and meals through these apps and add them to a shopping list. The shopping list keeps track of all the distinct ingredients of the cocktails and meals added to the shopping list.

They want to aim at a [minimum viable product](https://en.wikipedia.org/wiki/Minimum_viable_product): they have identified two APIs to use during this first phase:

* https://www.thecocktaildb.com/api.php
* https://www.themealdb.com/api.php

They plan to combine these two APIs in one of their own. They have a brand new Cloud-Native platform, based on OpenShift that they want to leverage.