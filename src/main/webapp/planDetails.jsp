<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: adam
  Date: 09.02.2021
  Time: 22:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">

<%@ include file="headLinks.jsp" %>

<body>
<%@ include file="headerSecond.jsp" %>

<section class="dashboard-section">
    <div class="row dashboard-nowrap">

        <%@ include file="menuBoczne.jsp" %>

        <div class="m-4 p-3 width-medium ">
            <div class="dashboard-content border-dashed p-3 m-4">
                <div class="row border-bottom border-3 p-1 m-1">
                    <div class="col noPadding">
                        <h3 class="color-header text-uppercase">SZCZEGÓŁY PLANU</h3>
                    </div>
                    <div class="col d-flex justify-content-end mb-2 noPadding">
                        <a href="#" class="btn btn-success rounded-0 pt-0 pb-0 pr-4 pl-4">Powrót</a>
                    </div>
                </div>

                <div class="schedules-content">
                    <div class="schedules-content-header">
                        <div class="form-group row">
                                <span class="col-sm-2 label-size col-form-label">
                                    Nazwa planu
                                </span>
                            <div class="col-sm-10">
                                <p class="schedules-text">${plan.name}</p>
                            </div>
                        </div>
                        <div class="form-group row">
                                <span class="col-sm-2 label-size col-form-label">
                                    Opis planu
                                </span>
                            <div class="col-sm-10">
                                <p class="schedules-text">
                                    ${plan.description}
                                </p>
                            </div>
                        </div>
                    </div>

                    <c:forEach items="${days}" var="day">

                        <table class="table">
                            <thead>
                            <tr class="d-flex">
                                <th class="col-2">${day}</th>
                                <th class="col-7"></th>
                                <th class="col-1"></th>
                                <th class="col-2"></th>
                            </tr>
                            </thead>

                            <c:forEach items="${planDetailsList}" var="planDetails">

                                <tbody class="text-color-lighter">
                                <c:if test="${planDetails.dayName==day}">
                                    <tr class="d-flex">
                                        <td class="col-2">${planDetails.mealName}</td>
                                        <td class="col-7">${planDetails.recipeName}</td>
                                        <td class="col-1 center">
                                            <a href="/app/plan/recipe/delete?recipeId=${planDetails.recipeId}" class="btn btn-danger rounded-0 text-light m-1">Usuń</a>
                                        </td>
                                        <td class="col-2 center">
                                            <a href="/app/plan/details" class="btn btn-info rounded-0 text-light m-1">Szczegóły</a>
                                        </td>
                                    </tr>
                                </c:if>
                                </tbody>

                            </c:forEach>

                        </table>
                    </c:forEach>

                </div>
            </div>
        </div>
    </div>
</section>


<%@ include file="footerSecond.jsp" %>
</body>
</html>