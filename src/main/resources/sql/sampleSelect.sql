-- SQL pobierający szczegóły posiłków w określonym planie
use scrumlab;

SELECT day_name.name as day_name, meal_name, recipe.name as recipe_name, recipe.description as recipe_description
FROM `recipe_plan`
JOIN day_name on day_name.id=day_name_id
JOIN recipe on recipe.id=recipe_id WHERE plan_id = 6 -- zamiast 6 należy wstawić id planu do pobrania --
ORDER by day_name.display_order, recipe_plan.display_order;


-- SQL - pobiera najnowszy plan dla zadanego użytkownika (tabela admins)
SELECT recipe_plan.id as id, day_name.name as day_name, meal_name,  recipe.name as recipe_name, recipe.description as recipe_description
FROM `recipe_plan`
JOIN day_name on day_name.id=day_name_id
JOIN recipe on recipe.id=recipe_id WHERE
recipe_plan.plan_id =  (SELECT MAX(plan.id) from plan WHERE admin_id = 2) -- zamiast 1 należy wstawić id użytkownika (tabela admins) --
ORDER by day_name.display_order, recipe_plan.display_order;

SELECT recipe_plan.id as id, plan.name as name_plan, day_name.name as day_name, meal_name,  recipe.name as recipe_name, recipe.description as recipe_description
FROM recipe_plan
JOIN day_name on day_name.id=day_name_id
JOIN recipe on recipe.id=recipe_id
JOIN plan on recipe_plan.plan_id = plan.id
WHERE recipe_plan.plan_id = (SELECT MAX(plan.id) from plan WHERE admin_id = 2)
ORDER by day_name.display_order, recipe_plan.display_order;

select * from admins;
select * from book;
select * from day_name;
select * from pages;
select * from plan;
select * from recipe;
select * from recipe_plan;

update plan set admin_id=2 where id=20;
use scrumlab;
select * from plan where admin_id=2;
select *from recipe where admin_id=2;

