-- SQL pobierający szczegóły posiłków w określonym planie
use scrumlab;

SELECT day_name.name as day_name, meal_name, recipe.name as recipe_name, recipe.description as recipe_description
FROM `recipe_plan`
JOIN day_name on day_name.id=day_name_id
JOIN recipe on recipe.id=recipe_id WHERE plan_id = 6 -- zamiast 6 należy wstawić id planu do pobrania --
ORDER by day_name.display_order, recipe_plan.display_order;


-- SQL - pobiera najnowszy plan dla zadanego użytkownika (tabela admins)
SELECT day_name.name as day_name, meal_name,  recipe.name as recipe_name, recipe.description as recipe_description
FROM recipe_plan
JOIN day_name on day_name.id=day_name_id
JOIN recipe on recipe.id=recipe_id
WHERE recipe_plan.plan_id =  (SELECT MAX(id) from plan WHERE admin_id = 1) -- zamiast 1 należy wstawić id użytkownika (tabela admins) --
ORDER by day_name.display_order, recipe_plan.display_order;

SELECT MAX(id) from plan WHERE admin_id = 1;


select * from admins;
select * from book;
select * from day_name;
select * from pages;
select * from plan;
select * from recipe;
select * from recipe_plan;
SELECT COUNT(*) AS size FROM recipe WHERE admin_id=0;
UPDATE recipe SET admin_id=3 WHERE id=5;

UPDATE recipe_plan SET plan_id=35 where id>4;

