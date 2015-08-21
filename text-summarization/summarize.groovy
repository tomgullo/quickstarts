package com.textteaser.summarizer
 
import net.codingwell.scalaguice.InjectorExtensions.ScalaInjector
import com.google.inject.Guice
import com.mongodb.*
import net.liftweb.mongodb.*
import net.liftweb.mongodb.record.*
import com.textteaser.summarizer.models.Keyword
 
import com.foursquare.rogue.LiftRogue.*
import org.json4s.*
import org.slf4j.*
 
def config = new Config()
 
def injector = Guice.createInjector(
    new GuiceModule(config, true))
 
def summarizer = injector.getInstance( Summarizer.class )
def log = injector.getInstance(Logger.class)
 
def title = "Here is a title"
def text = "Zombies are scary"
 
def article = new Article("not_important", title, text, "", "", "") 
 
def summary = summarizer.summarize(article.article, 
article.title, article.id, article.blog, article.category)
println summary
