/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.grouter.domain.entities;

import org.hibernate.search.annotations.*;
import org.hibernate.validator.Email;

import javax.persistence.*;


/**
 * Domain class.
 *
 * @Author Georges Polyzois
 */

@Entity
@Table(name = "address")
@Indexed(index = "indexes/address")
public class Address extends BaseEntity
{
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @DocumentId
    private Long id;

    @Column(name = "idno", nullable = true)
    private String idNo;

    @Transient
    private Country country;

    @Column(name = "phone")
    @Field(index = Index.TOKENIZED, store = Store.YES)
    private String phone;

    @Column(name = "mobilephone")
    @Field(index = Index.TOKENIZED, store = Store.YES)
    private String mobilephone;

    @Column(name = "street")
    @Field(index = Index.TOKENIZED, store = Store.YES)
    private String street;

    @Column(name = "zip")
    @Field(index = Index.TOKENIZED, store = Store.YES)
    private String zip;

    @Column(name = "city")
    @Field(index = Index.TOKENIZED, store = Store.YES)
    private String city;

    @Column(name = "fax")
    @Field(index = Index.TOKENIZED, store = Store.YES)
    private String fax;

    @Column(name = "homepageurl")
    @Field(index = Index.TOKENIZED, store = Store.YES)
    private String homepageUrl;

    @Column(name = "companyname")
    @Field(index = Index.TOKENIZED, store = Store.YES)
    private String companyname;

    @Column(name = "email")
    @Email
    @Field(index = Index.TOKENIZED, store = Store.YES)
    private String email;

    public Address()
    {
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Country getCountry()
    {
        return country;
    }

    public void setCountry(Country country)
    {
        this.country = country;
    }


    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getMobilephone()
    {
        return mobilephone;
    }

    public void setMobilephone(String mobilephone)
    {
        this.mobilephone = mobilephone;
    }

    public String getStreet()
    {
        return street;
    }

    public void setStreet(String street)
    {
        this.street = street;
    }

    public String getZip()
    {
        return zip;
    }

    public void setZip(String zip)
    {
        this.zip = zip;
    }

    public String getCity()
    {
        return city;
    }

    public void setCity(String city)
    {
        this.city = city;
    }

    public String getFax()
    {
        return fax;
    }

    public void setFax(String fax)
    {
        this.fax = fax;
    }

    public String getHomepageUrl()
    {
        return homepageUrl;
    }

    public void setHomepageUrl(String homepageUrl)
    {
        this.homepageUrl = homepageUrl;
    }

    public String getCompanyname()
    {
        return companyname;
    }

    public void setCompanyname(String companyname)
    {
        this.companyname = companyname;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }


    public String getIdNo()
    {
        return idNo;
    }

    public void setIdNo(String idNo)
    {
        this.idNo = idNo;
    }
}
